package com.nnm.team91.mine.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimelineListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimelineListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TimelineAdapter adapter;
    ListView timelineListview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TimelineListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineListFragment newInstance(String param1, String param2) {
        TimelineListFragment fragment = new TimelineListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_timeline_list, container, false);
        timelineListview = (ListView) view.findViewById(R.id.listview_timeline);

        adapter = new TimelineAdapter();

        timelineListview.setAdapter(adapter);

        // Change MainActivity ViewMode if ListItem is selected
        timelineListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                // get item
//                TimelineItem item = (TimelineItem) parent.getItemAtPosition(position);
//
//                String titleStr = item.getTitle();
//                String descStr = item.getDesc();
//                Drawable iconDrawable = item.getIcon();
                // TODO : use item data.

//                Toast toast = Toast.makeText(getContext(),
//                        position + " : " + titleStr + descStr, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();

                MainActivity main = (MainActivity) getActivity();
                main.ChangePageMode();
            }
        });

        // Set ListView Focus to Current Item
        timelineListview.post(new Runnable() {
            @Override
            public void run() {
//                timelineListview.scrollTo(0, timelineListview.getBottom());
                timelineListview.setSelection(timelineListview.getAdapter().getCount() - 1);
            }
        });

        AddDummyItem();
        AddDummyItem();
        AddDummyItem();
        AddDummyItem();
        AddDummyItem();

        return view;
    }

    public void AddDummyItem() {
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_account_box_black_36dp),
                "Box", "Account Box Black 36dp");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_account_circle_black_36dp),
                "Circle", "Account Circle Black 36dp");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_assignment_ind_black_36dp),
                "Ind", "Assignment Ind Black 36dp");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}