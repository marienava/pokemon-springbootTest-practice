package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview() {

        //Arrange
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        //Act
        Review savedReview = reviewRepository.save(review);

        //Assert
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThanOneReview() {
        //Arrange
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        Review review2 = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        reviewRepository.save(review);
        reviewRepository.save(review2);

        //Act
        List<Review> reviewList = reviewRepository.findAll();

        //Assert
        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);


    }

    @Test
    public void ReviewRepository_FindById_ReturnSavedReview() {

        //Arrange
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        //Act
        reviewRepository.save(review);
        Review returnReview = reviewRepository.findById(review.getId()).get();

        //Assert
        Assertions.assertThat(returnReview).isNotNull();

    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnUpdatedReview() {

        //Arrange
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        //Act
        reviewRepository.save(review);
        Review savedReview = reviewRepository.findById(review.getId()).get();
        savedReview.setTitle("new title");
        savedReview.setContent("new content");
        savedReview.setStars(4);
        Review updatedReview = reviewRepository.save(savedReview);

        //Assert
        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
        Assertions.assertThat(updatedReview.getContent()).isNotNull();
        Assertions.assertThat(updatedReview.getStars()).isNotNull();

    }

    @Test
    public void ReviewRepository_DeleteById_ReturnReviewIsEmpty() {

        //Arrange
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        //Act
        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> returnReview = reviewRepository.findById(review.getId());

        //Assert
        Assertions.assertThat(returnReview).isEmpty();

    }




}
