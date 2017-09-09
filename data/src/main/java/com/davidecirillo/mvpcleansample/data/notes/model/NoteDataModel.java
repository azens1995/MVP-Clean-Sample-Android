package com.davidecirillo.mvpcleansample.data.notes.model;


public class NoteDataModel {

    private String mTitle;
    private String mText;
    private Long mCreatedAt;

    public NoteDataModel(String title, String text, Long createdAt) {
        mTitle = title;
        mText = text;
        mCreatedAt = createdAt;
    }

    public String getText() {
        return mText;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteDataModel that = (NoteDataModel) o;

        return getText().equals(that.getText()) && getCreatedAt().equals(that.getCreatedAt());

    }

    @Override
    public int hashCode() {
        int result = getText().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        return result;
    }
}
