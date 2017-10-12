// Generated code from Butter Knife. Do not modify!
package com.keehoo.kree.budgetguru.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.keehoo.kree.budgetguru.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserCreatorActivity_ViewBinding implements Unbinder {
  private UserCreatorActivity target;

  private View view2131558543;

  @UiThread
  public UserCreatorActivity_ViewBinding(UserCreatorActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserCreatorActivity_ViewBinding(final UserCreatorActivity target, View source) {
    this.target = target;

    View view;
    target.login = Utils.findRequiredViewAsType(source, R.id.login, "field 'login'", EditText.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", EditText.class);
    target.lastName = Utils.findRequiredViewAsType(source, R.id.lastname, "field 'lastName'", EditText.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.email, "field 'email'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.create_user, "field 'createUserButton' and method 'createUser'");
    target.createUserButton = Utils.castView(view, R.id.create_user, "field 'createUserButton'", Button.class);
    view2131558543 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.createUser();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserCreatorActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.login = null;
    target.name = null;
    target.lastName = null;
    target.email = null;
    target.password = null;
    target.createUserButton = null;

    view2131558543.setOnClickListener(null);
    view2131558543 = null;
  }
}
