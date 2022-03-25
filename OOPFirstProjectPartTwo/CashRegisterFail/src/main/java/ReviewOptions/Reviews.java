package ReviewOptions;


import ItemOptions.ItemOptions;

public class Reviews {
    private int reviewGrade;
    private String reviewComment;


    public Reviews(int reviewGrade, String reviewComment) {
        this.reviewGrade = reviewGrade;
        this.reviewComment = reviewComment;
    }

    public int getReviewGrade() {
        return reviewGrade;
    }

    public void setReviewGrade(int reviewGrade) {
        this.reviewGrade = reviewGrade;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public String toString() {
        return reviewGrade + "." + reviewComment;
    }


    }

