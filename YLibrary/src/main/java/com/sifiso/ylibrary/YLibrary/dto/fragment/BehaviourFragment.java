package com.sifiso.ylibrary.YLibrary.dto.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sifiso.ylibrary.YLibrary.R;
import com.sifiso.ylibrary.YLibrary.dto.ResponseDTO;
import com.sifiso.ylibrary.YLibrary.dto.adapter.IssueAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.sifiso.ylibrary.YLibrary.dto.fragment.BehaviourFragment.OnFragmentBehaviourListener} interface
 * to handle interaction events.
 * Use the {@link BehaviourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BehaviourFragment extends Fragment {
    private ResponseDTO response;
    private ListView BH_LV_list;
    private TextView BH_txtNoOfAtt;

    private Context ctx;
    private Activity activity;
    private OnFragmentBehaviourListener mListener;
    private IssueAdapter issueAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BehaviourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BehaviourFragment newInstance(ResponseDTO response) {
        BehaviourFragment fragment = new BehaviourFragment();
        Bundle args = new Bundle();
        args.putSerializable("response", response);
        fragment.setArguments(args);
        return fragment;
    }

    public BehaviourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void build() {
        BH_LV_list = (ListView) view.findViewById(R.id.BH_LV_list);
        BH_txtNoOfAtt = (TextView) view.findViewById(R.id.BH_txtNoOfAtt);
        BH_txtNoOfAtt.setText("" + response.getStudent().getIssueList().size());
        issueAdapter = new IssueAdapter(ctx, R.layout.attendance_view, response.getStudent().getIssueList());
        BH_LV_list.setAdapter(issueAdapter);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_behaviour, container, false);
        ctx = getActivity().getApplicationContext();
        activity = getActivity();
        if (getArguments() != null) {
            response = (ResponseDTO) getArguments().getSerializable("response");
            build();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ResponseDTO resp) {
        if (mListener != null) {
            mListener.onBehaviourInteraction(resp);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentBehaviourListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentBehaviourListener {
        // TODO: Update argument type and name
        public void onBehaviourInteraction(ResponseDTO resp);
    }

}
