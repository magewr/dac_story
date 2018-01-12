package com.example.wr.story.ui.content.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.listener.OnItemClickListener;
import com.example.wr.story.ui.util.AndroidUtil;
import com.example.wr.story.ui.util.Navigator;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.wr.story.ui.content.camera.CameraActivity.RESULT_IMAGE_LIST_KEY;

/**
 * Detail Activity
 * 스토리 아이템을 선택했을 때 나오는 자세히 보기 Activity
 * 보기모드와 편집모드 두 기능을 지원하며
 * 보기모드에는 이미지 목록/원본, 제목, 메모, 최종수정시각을 볼수 있으며
 * 편집모드일 경우에는 보기모드에 추가로 이미지 추가/삭제, 제목, 메모가 편집이 가능해진다.
 * 이 액티비티에서 사용되는 StoryItem은 원본의 복사본으로
 * 수정 중에는 이 복사본에 데이터를 Update하고
 * 수정이 완료되면 이 복사본을 가지고 Presenter에 Update를 요청하고
 * 수정이 취소될 때에는 Presenter로부터 원본을 다시 받아서 뷰를 세팅한다.
 *
 * 자식 Activity로 Add Activity가 있다.
 */
public class DetailActivity extends BaseActivity implements DetailContract.View {

    private static final String STORY_ID = "storyId";
    /**
     * Calling Intent 받아오는 메소드, Calling Intent는 Navigator에서 사용된다.
     * @param context Intent의 Caller context
     * @param storyId Detail 보여줄 Story Item의 ID값
     * @return  생성된 Intent
     */
    public static Intent getCallingIntent(Context context, long storyId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
        return intent;
    }

    @BindView(R.id.text_date)       protected TextView dateTextView;
    @BindView(R.id.edit_title)      protected EditText titleEditText;
    @BindView(R.id.edit_memo)       protected EditText memoEditText;
    @BindView(R.id.detail_edit_fab) protected FloatingActionButton editFab;
    @BindView(R.id.image_viewpager) protected ViewPager viewPager;
    @BindView(R.id.text_indicator)  protected TextView indicatorTextView;

    @Inject       DetailPresenter presenter;
    protected     ThumbnailViewPagerAdapter adapter;    // 썸네일 뷰페이저 어댑터
    private       StoryDTO tempStoryItem;               // 현재 보여지는 item의 복사본, 원본은 presenter만 소유


    /////////////////////////////////////////
    // Base Activity 메소드 구현부

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initDagger() {
        activityComponent = getApplicationComponent().activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
    }

    @Override
    protected void initPresenter() {
        super.presenter = presenter;
        presenter.setView(this);
    }


    ///////////////////////////////////////////
    // Activity 메소드 오버라이드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOwnData();
        initViewPager();
        presenter.changeDisplayModeTo(getInitDisplayMode());
    }

    @Override
    public void onBackPressed() {
        //Presenter에서 판단 후 consume일 경우엔 무시
        if (presenter.handleOnBackPressed() == true)
            return;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Camera Activity 결과 핸들링
        if (resultCode == RESULT_OK) {
            ArrayList<String> pathListFromCamera = data.getStringArrayListExtra(RESULT_IMAGE_LIST_KEY);
            if (pathListFromCamera != null) {
                presenter.onPictureAdded(pathListFromCamera);
            }
        }
    }


    ///////////////////////////////////////////
    // 오버라이드 필요한 메소드 구현

    /**
     * onCreate 시에 protected가 아닌 private한 데이터를 설정하는 메소드
     */
    protected void initOwnData() {
        long storyId = getIntent().getLongExtra(STORY_ID, -1);
        presenter.setStoryById(storyId, (isSuccess, msg) -> {
            if (!isSuccess) {
                Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * viewPager를 init할 때 자신만의 adapter 세팅을 위한 메소드
     */
    protected void initViewPager() {
        adapter = new ThumbnailViewPagerAdapter(getSupportFragmentManager(), getInitDisplayMode(), new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Navigator.toGalleryActivity(DetailActivity.this, getDetailedStoryItem().getImagePathList(), position);
            }

            @Override
            public void onRemoveItemClick(int position) {
                getDetailedStoryItem().getImagePathList().remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAddItemClick() {
                Navigator.toCameraActivityForResult(DetailActivity.this);
            }
        });
        adapter.setImageListChangedListener(list -> updateIndicatorText(viewPager.getCurrentItem()));
        presenter.setAdapterModel(adapter);
        viewPager.setAdapter(adapter);
        //Thumbnail 개수 indicator listener 설정
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                presenter.calculatePageIndicatorIndex(position);
            }
        });
    }

    /**
     * 최초 시작 시에 보여질 모드를 리턴하는 메소드, Detail Activity는 기본 ViewMode
     * @return 최초 시작 시 보여질 모드
     */
    protected DisplayMode getInitDisplayMode() {
        return DisplayMode.ViewMode;
    }

    /**
     * 현재 편집중인 아이템을 제공하는 메소드.
     * 부모/자식 액티비티의 동일한 루틴에서 타겟 아이템만 다른경우에 이 메소드로 아이템을 제공받는다.
     * @return 편집중인 아이템
     */
    protected StoryDTO getDetailedStoryItem() {
        return tempStoryItem;
    }

    @OnClick(R.id.detail_edit_fab)
    protected void onEditFabClicked() {
        switch (presenter.getCurrentDisplayMode()) {
            case EditMode:
                saveModifiedStory();
                break;
            case ViewMode:
                presenter.changeDisplayModeTo(DisplayMode.EditMode);
                break;
        }
    }


    /////////////////////////////////
    // Contract method 구현

    @Override
    public void onGetStory() {
        tempStoryItem = presenter.copyDetailStoryItem();
        titleEditText.setText(tempStoryItem.getTitle());
        memoEditText.setText(tempStoryItem.getMemo());
        dateTextView.setText(StoryItemUtil.getDateString(tempStoryItem.getDate()));
        adapter.setImagePathList(tempStoryItem.getImagePathList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCancelEditingAlertDialog() {
        AndroidUtil.showAlertDialog(this, R.string.detail_dialog_cancel_modify_message, (dialogInterface, i) -> {
            rollbackModifiedStory();
        });
    }

    private void saveModifiedStory () {
        tempStoryItem.setDate(new Date());
        tempStoryItem.setTitle(titleEditText.getText().toString());
        tempStoryItem.setMemo(memoEditText.getText().toString());

        presenter.updateStory(tempStoryItem, (isSuccess, msg) -> {
            if (isSuccess) {
                Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_success), Toast.LENGTH_SHORT).show();
                // 성공 시에 presenter로부터 새롭게 아이템을 복사받아서 현재 story로 설정
                tempStoryItem = presenter.copyDetailStoryItem();
            }
            else
                Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_error) + msg, Toast.LENGTH_SHORT).show();
        });
    }

     @Override
     public void rollbackModifiedStory() {
        tempStoryItem = presenter.copyDetailStoryItem();
        adapter.setImagePathList(tempStoryItem.getImagePathList());
        titleEditText.setText(tempStoryItem.getTitle());
        memoEditText.setText(tempStoryItem.getMemo());
        presenter.changeDisplayModeTo(DisplayMode.ViewMode);
    }

    @Override
    public void initViewByDisplayMode(DisplayMode displayMode) {
        switch (displayMode) {
            case EditMode:
                titleEditText.setEnabled(true);
                memoEditText.setFocusable(true);
                memoEditText.setFocusableInTouchMode(true);
                editFab.setImageResource(R.drawable.done_white);
                dateTextView.setVisibility(View.GONE);
                break;

            case ViewMode:
                titleEditText.setEnabled(false);
                memoEditText.setFocusable(false);
                memoEditText.setFocusableInTouchMode(false);
                editFab.setImageResource(R.drawable.edit_white);
                dateTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void setViewPagerIndicatorText(int current, int max) {
        indicatorTextView.setText(String.format(Locale.getDefault(),"%d / %d", current, max));
    }

    private void updateIndicatorText (int currentPosition) {
        int imagePosition = ++currentPosition > adapter.getImageCount() ? --currentPosition : currentPosition;
        indicatorTextView.setText(String.format(Locale.getDefault(),"%d / %d", imagePosition, adapter.getImageCount()));
    }

}
