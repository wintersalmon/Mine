package com.nnm.team91.mine.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nnm.team91.mine.DetailTodoActivity;
import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.R;
import com.nnm.team91.mine.adapter.HashTagAdapter;
import com.nnm.team91.mine.data.HashTagData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link ListFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HashTagListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HashTagListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HashTagListFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HashTagAdapter getAdapter() {
        return adapter;
    }

    private HashTagAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public HashTagListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HashTagListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HashTagListFragment newInstance(String param1, String param2) {
        HashTagListFragment fragment = new HashTagListFragment();
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
        adapter = new HashTagAdapter();
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // TODO: 2016. 12. 13. Add Open Item Detail View Function
        Toast.makeText(getContext(), "링크 구현 예정 : " + position, Toast.LENGTH_SHORT).show();

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

    public void updateHashTagAdapter(ArrayList<HashTagData> tagList) {
        adapter.clear();
        for (HashTagData hashTag : tagList) {
            adapter.addItem(hashTag);
        }
        getAdapter().notifyDataSetChanged();
        Toast.makeText(getContext(), "검색 완료", Toast.LENGTH_SHORT).show();
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
//        void updateHashTagAdapter(HashTagAdapter adapter);
    }
}
