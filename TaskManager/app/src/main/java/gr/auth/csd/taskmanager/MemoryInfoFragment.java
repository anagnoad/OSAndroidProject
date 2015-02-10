package gr.auth.csd.taskmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import gr.auth.csd.taskmanager.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MemoryInfoFragment extends ListFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /**
     * Title name for listview item.
     */
    private static final String ARG_PARAM1 = "param1";
    /**
     * Subtitle name for listview item.
     */
    private static final String ARG_PARAM2 = "param2";
    /**
     * The listview shown in the fragment.
     */

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * static initialization of List Fragment with two params
     * @param param1 The list item title
     * @param param2 The list item subtitle
     * @return
     */
    public static MemoryInfoFragment newInstance(String param1, String param2) {
        MemoryInfoFragment fragment = new MemoryInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemoryInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        DBHandler db = new DBHandler(this.getActivity().getApplicationContext());
        try {
            List<MemoryInfo> memoryInfoList = db.getAllRecords();
            setListAdapter(new ArrayAdapter<MemoryInfo>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, memoryInfoList));
        }
        catch (ParseException e)
        {

        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
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
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
