// Generated code from Butter Knife. Do not modify!
package com.keehoo.kree.budgetguru.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.keehoo.kree.budgetguru.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131558531;

  private View view2131558532;

  private View view2131558534;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.create_new_user, "field 'createUserButton' and method 'startNewUserActivity'");
    target.createUserButton = Utils.castView(view, R.id.create_new_user, "field 'createUserButton'", Button.class);
    view2131558531 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startNewUserActivity();
      }
    });
    view = Utils.findRequiredView(source, R.id.create_budget_entry, "field 'createBudgetEntryButton' and method 'startNewBudgetEntryActivity'");
    target.createBudgetEntryButton = Utils.castView(view, R.id.create_budget_entry, "field 'createBudgetEntryButton'", Button.class);
    view2131558532 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startNewBudgetEntryActivity();
      }
    });
    target.loginButton = Utils.findRequiredViewAsType(source, R.id.login, "field 'loginButton'", Button.class);
    view = Utils.findRequiredView(source, R.id.ocr, "field 'ocrButton' and method 'startOcrActivity'");
    target.ocrButton = Utils.castView(view, R.id.ocr, "field 'ocrButton'", Button.class);
    view2131558534 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startOcrActivity();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.createUserButton = null;
    target.createBudgetEntryButton = null;
    target.loginButton = null;
    target.ocrButton = null;

    view2131558531.setOnClickListener(null);
    view2131558531 = null;
    view2131558532.setOnClickListener(null);
    view2131558532 = null;
    view2131558534.setOnClickListener(null);
    view2131558534 = null;
  }
}
