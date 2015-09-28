package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import butterknife.Bind;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerActivityComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.fragment.LoadFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserListFragment;


/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity implements HasComponent<ApplicationComponent>,
        UserListFragment.UserListListener {
  private ActivityComponent activityComponent;
  private ActivityModule activityModule;

  @Bind(R.id.btn_LoadData) Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_main);
    this.initializeInjector();
    navigator.navigateToLoadFragment(this);
  }

  private void initializeInjector() {
    this.activityModule = getActivityModule();
    this.activityComponent = DaggerActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(activityModule)
        .build();
  }

  @Override
  public ApplicationComponent getComponent() {
    return getApplicationComponent();
  }

  public ActivityModule getActivityModule() {
    return activityModule == null ? new ActivityModule(this) : activityModule;
  }

  @Override public void onUserClicked(UserModel userModel) {
    this.navigator.navigateToUserDetails(this, userModel.getUserId());
  }

  public void navigateToUserList() {
    navigator.navigateToUserList(this); // TODO: use Listener with 'public void onLoadClicked' or something
  }
}
