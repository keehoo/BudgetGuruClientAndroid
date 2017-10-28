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

public class BudgetEntryActivity_ViewBinding implements Unbinder {
  private BudgetEntryActivity target;

  private View view2131230740;

  @UiThread
  public BudgetEntryActivity_ViewBinding(BudgetEntryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BudgetEntryActivity_ViewBinding(final BudgetEntryActivity target, View source) {
    this.target = target;

    View view;
    target.cost = Utils.findRequiredViewAsType(source, R.id.cost, "field 'cost'", EditText.class);
    target.userId = Utils.findRequiredViewAsType(source, R.id.user_id, "field 'userId'", EditText.class);
    view = Utils.findRequiredView(source, R.id.add, "field 'add' and method 'addNewBudgetEntry'");
    target.add = Utils.castView(view, R.id.add, "field 'add'", Button.class);
    view2131230740 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addNewBudgetEntry();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    BudgetEntryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cost = null;
    target.userId = null;
    target.add = null;

    view2131230740.setOnClickListener(null);
    view2131230740 = null;
  }
}
