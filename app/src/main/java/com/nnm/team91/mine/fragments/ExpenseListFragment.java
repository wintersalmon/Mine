package com.nnm.team91.mine.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.adapter.ExpenseAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnExpenseFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseListFragment extends ListFragment implements AbsListView.OnScrollListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ExpenseAdapter adapter;

    private int currentPosition;

    public ExpenseAdapter getAdapter() {
        return adapter;
    }

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
        adapter = new ExpenseAdapter();
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnScrollListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        MainActivity main = (MainActivity) getActivity();
        if (main == null)
            return;
        if (isVisibleToUser) {
            currentPosition = main.getSelectedPosition();
            getListView().setSelectionFromTop(currentPosition, 0);
        } else {
            main.setSelectedPosition(currentPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.updateExpenseAdapter(adapter);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentPosition = view.getFirstVisiblePosition();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        Toast.makeText(getContext(), "ExpenseList", Toast.LENGTH_SHORT).show();
        MainActivity main = (MainActivity) getActivity();
        main.DetailExpense(position);
    }

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