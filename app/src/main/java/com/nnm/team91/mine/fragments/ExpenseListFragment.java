package com.nnm.team91.mine.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nnm.team91.mine.R;
import com.nnm.team91.mine.adapter.ExpenseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnExpenseFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ExpenseAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnExpenseFragmentInteractionListener mListener;

    public ExpenseListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseListFragment newInstance(String param1, String param2) {
        ExpenseListFragment fragment = new ExpenseListFragment();
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
//        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, LIST_MENU);
//
//        ListView listview = (ListView) view.findViewById(R.id.listview_expense);
//        listview.setAdapter(adapter);
//
//        return view;

        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        ListView listview = (ListView) view.findViewById(R.id.listview_expense);

        adapter = new ExpenseAdapter();

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
//                ExpenseData item = (ExpenseData) parent.getItemAtPosition(position) ;
//
//                String titleStr = item.getTitle() ;
//                String descStr = item.getDesc() ;
//                Drawable iconDrawable = item.getIcon() ;

                // TODO : use item data.
            }
        }) ;

//        addDummy();

        mListener.updateExpenseAdapter(adapter);

        return view;
    }

//    public void addDummy() {
//        ArrayList<String> hashtags = new ArrayList<String>();
//
//        hashtags.add("Happy");
//        hashtags.add("Halloween");
//        hashtags.add("October");
//        Date datetime = Calendar.getInstance().getTime();
//        for (int i=0; i<20; i++) {
//            adapter.addItem(datetime, i*1000, "사용 목적", hashtags, i%3);
//        }
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onExpenseFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExpenseFragmentInteractionListener) {
            mListener = (OnExpenseFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDiaryListFragmentInteractionListener");
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
    public interface OnExpenseFragmentInteractionListener {
        // TODO: Update argument type and name
        void onExpenseFragmentInteraction(Uri uri);
        void updateExpenseAdapter(ExpenseAdapter adapter);
    }
}