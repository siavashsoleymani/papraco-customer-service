package com.papraco.customerservice.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.papraco.customerservice.domain.UserComment} entity.
 */
public class UserCommentDTO implements Serializable {

    private String id;

    private String comment;

    private Integer score;

    private CompanyDTO company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCommentDTO)) {
            return false;
        }

        UserCommentDTO userCommentDTO = (UserCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCommentDTO{" +
            "id='" + getId() + "'" +
            ", comment='" + getComment() + "'" +
            ", score=" + getScore() +
            ", company='" + getCompany() + "'" +
            "}";
    }
}
