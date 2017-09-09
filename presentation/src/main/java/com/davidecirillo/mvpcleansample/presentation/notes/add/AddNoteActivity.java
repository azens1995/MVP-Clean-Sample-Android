package com.davidecirillo.mvpcleansample.presentation.notes.add;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.presentation.common.BaseActivity;

public class AddNoteActivity extends BaseActivity implements AddNoteContract.View {

    public final static String KEY_NOTE_VIEW_MODEL_EXTRA = "NOTE_VIEW_MODEL_EXTRA";

    private EditText mContentEditText;
    private EditText mTitleEditText;
    private AddNotePresenter mPresenter;

    public static Intent getIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_note);

        createPresenter();

        mContentEditText = (EditText) findViewById(R.id.note_text);
        mTitleEditText = (EditText) findViewById(R.id.note_title);

        // Request focus on first edit text
        mTitleEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add new note");
        }
    }

    @Override
    public void closeSectionWithPositiveResult() {
        setResult(RESULT_OK, new Intent());
        finish();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    protected void createPresenter() {
        ValidateFieldsUseCase validateFieldsUseCase = new ValidateFieldsUseCase();
        SaveNoteUseCase saveNoteUseCase = new SaveNoteUseCase(mPrefsNoteRepository);
        mPresenter = new AddNotePresenter(mUseCaseHandler, validateFieldsUseCase, saveNoteUseCase);
        bindPresenterToView(mPresenter);
    }

    public void onAddNoteClick(View view) {
        mPresenter.validateFields(
                mContentEditText.getText().toString(),
                mTitleEditText.getText().toString()
        );
    }
}
