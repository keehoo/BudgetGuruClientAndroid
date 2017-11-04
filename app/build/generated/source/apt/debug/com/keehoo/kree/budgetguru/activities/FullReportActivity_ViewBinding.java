// Generated code from Butter Knife. Do not modify!
package com.keehoo.kree.budgetguru.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.keehoo.kree.budgetguru.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FullReportActivity_ViewBinding implements Unbinder {
  private FullReportActivity target;

  private View view2131230898;

  @UiThread
  public FullReportActivity_ViewBinding(FullReportActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FullReportActivity_ViewBinding(final FullReportActivity target, View source) {
    this.target = target;

    View view;
    target.sum = Utils.findRequiredViewAsType(source, R.id.sum, "field 'sum'", TextView.class);
    target.date = Utils.findRequiredViewAsType(source, R.id.date, "field 'date'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", TextView.class);
    view = Utils.findRequiredView(source, R.id.upload, "field 'uploadButton' and method 'upload'");
    target.uploadButton = Utils.castView(view, R.id.upload, "field 'uploadButton'", Button.class);
    view2131230898 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.upload();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FullReportActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sum = null;
    target.date = null;
    target.time = null;
    target.uploadButton = null;

    view2131230898.setOnClickListener(null);
    view2131230898 = null;
  }
}
