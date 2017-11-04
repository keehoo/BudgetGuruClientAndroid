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

public class OcrResultAnalysisActivity_ViewBinding implements Unbinder {
  private OcrResultAnalysisActivity target;

  private View view2131230861;

  @UiThread
  public OcrResultAnalysisActivity_ViewBinding(OcrResultAnalysisActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OcrResultAnalysisActivity_ViewBinding(final OcrResultAnalysisActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.seeFullReportButtonId, "field 'seeFullReportButton' and method 'seeFullReport'");
    target.seeFullReportButton = Utils.castView(view, R.id.seeFullReportButtonId, "field 'seeFullReportButton'", Button.class);
    view2131230861 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.seeFullReport();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    OcrResultAnalysisActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.seeFullReportButton = null;

    view2131230861.setOnClickListener(null);
    view2131230861 = null;
  }
}
