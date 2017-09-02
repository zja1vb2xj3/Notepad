package com.example.user.notepad;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private NotepadModel notepadModel;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ArrayList<Fragment> fragments = getFragments();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
    }

    private ArrayList<Fragment> getFragments() {
        final String MODEL_KEY = "NotepadModel";
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        if(getIntent() != null){
            Intent intent = getIntent();

            notepadModel = (NotepadModel) intent.getSerializableExtra(MODEL_KEY);

            for(int i=0; i<notepadModel.getNoteDatas().size(); i++){
                fragments.add(PageFragment.newInstance(notepadModel.getNoteDatas().get(i)));
            }
        }

        return fragments;
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        /**
         * @return notepadModel객체 NoteDatas ArrayList size
         */
        @Override
        public int getCount() {
            return this.fragments.size();
        }


    }

    public static class PageFragment extends Fragment {
        private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

        public static final PageFragment newInstance(String message) {
            PageFragment pageFragment = new PageFragment();
            Bundle args = new Bundle();
            args.putString(EXTRA_MESSAGE, message);
            pageFragment.setArguments(args);

            return pageFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String message = getArguments().getString(EXTRA_MESSAGE);

            View rootView = inflater.inflate(R.layout.fragment_page, container, false);

            TextView fragmentTextView = (TextView) rootView.findViewById(R.id.fragmentTextView);
            fragmentTextView.setText(message);

            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.i("onDestroyViewSign", "onDestroyView");
        }
    }//end ArrayListFragment

}
