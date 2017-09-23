package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import cloud.safe.com.kuchmynda.mark.safecloud.R;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Activities.NavigationDrawerActivity;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.StructureFragment;

import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.FRAGMENT_ID_EXTRA;
import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.FRAGMENT_MODEL_EXTRA;

/**
 * Created by Markiian Kuchmynda on 23.09.2017.
 */

public class StructurePresenter extends PresenterBase<StructureFragment> {

    @Override
    protected void initModel() {

    }

    public void goTo(int position){
        Activity activity=view.getActivity();
        Intent intent = new Intent(activity, NavigationDrawerActivity.class);
        intent.putExtra(FRAGMENT_ID_EXTRA, StructureFragment.ID);
        View view1=view.getView();
        intent.putExtra(FRAGMENT_MODEL_EXTRA, view.getModel().get(position));
        //todo: fix animation
        assert view1 != null;
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<>(view1.findViewById(R.id.structure_item_header), activity.getString(R.string.transist1)),
                new Pair<>(view1.findViewById(R.id.structure_item_date), activity.getString(R.string.transist2)));
        view.getActivity().startActivity(intent, optionsCompat.toBundle());
    }
}
