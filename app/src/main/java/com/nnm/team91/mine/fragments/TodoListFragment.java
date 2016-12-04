package com.nnm.team91.mine.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nnm.team91.mine.DetailTodoActivity;
import com.nnm.team91.mine.MainActivity;
import com.nnm.team91.mine.R;
import com.nnm.team91.mine.adapter.TodoAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTodoFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TodoAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnTodoFragmentInteractionListener mListener;

    public TodoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodoListFragment newInstance(String param1, String param2) {
        TodoListFragment fragment = new TodoListFragment();
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
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        ListView listview = (ListView) view.findViewById(R.id.listview_todo);

        // Make Checkbox not to conflict with setOnItemClickListener
        listview.setItemsCanFocus(false);

        // Adapter 생성
        adapter = new TodoAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // TODO: 2016. 12. 2. add function to OnItemClickListener
                MainActivity main = (MainActivity) getActivity();
                main.DetailTodo();
            }
        }) ;

        mListener.updateTodoAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTodoFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTodoFragmentInteractionListener) {
            mListener = (OnTodoFragmentInteractionListener) context;
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
    public interface OnTodoFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTodoFragmentInteraction(Uri uri);
        void updateTodoAdapter(TodoAdapter adapter);
    }
}