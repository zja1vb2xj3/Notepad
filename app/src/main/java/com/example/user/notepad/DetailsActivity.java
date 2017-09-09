package com.example.user.notepad;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private NotepadModel notepadModel;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int itemCount;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getNotepadModel();
        ArrayList<Fragment> fragments = getFragments();
        setViewPager(fragments);

    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        for (int i = 0; i < notepadModel.getNoteDatas().size(); i++)
            fragments.add(PageFragment.newInstance(notepadModel.getNoteDatas().get(i)));

        return fragments;
    }

    private void setViewPager(ArrayList<Fragment> fragments) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                itemPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedposition / 2 + 0.5f);
                page.setScaleY(normalizedposition / 2 + 0.5f);
            }
        });


        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(notepadModel.getDataPosition());
    }//end setViewPager

    private void getNotepadModel() {
        final String MODEL_KEY = "NotepadModel";

        if (getIntent() != null) {
            Intent intent = getIntent();
            notepadModel = (NotepadModel) intent.getSerializableExtra(MODEL_KEY);
            itemCount = notepadModel.getNoteDatas().size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.modifyNoteButton) {
            modifyNoteOptionButtonClick();
        }

        return super.onOptionsItemSelected(item);
    }

    private void modifyNoteOptionButtonClick() {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        notepadModel.setDataPosition(itemPosition);
        final String MODLE_KEY = "NotepadModel";
        intent.putExtra(MODLE_KEY, notepadModel);
        startActivity(intent);
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        /**
         * @return notepadModel객체 NoteDatas ArrayList size
         */
        @Override
        public int getCount() {
            Log.i("getCount", String.valueOf(itemCount));
            return fragments.size();
        }

    }//end PagerAdapter.class

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

            final int textSize = getResources().getInteger(R.integer.noteTextSize);
            fragmentTextView.setTextSize(textSize);

            fragmentTextView.setText(message);

            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.i("onDestroyViewSign", "onDestroyView");
        }

    }//end PageFragment.class

}
