package com.example.mydailytime_2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailytime_2.dialog.InputDayItemDialog;
import com.example.mydailytime_2.helper.DayItemVO;
import com.example.mydailytime_2.viewModel.DayItemViewModel;
import com.google.android.material.internal.CheckableImageButton;

import java.util.Objects;


public class DayItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TODAY_DATE ="today_date";
    private static DayItemFragment dayItemFragment;
    MyDayItemRecyclerViewAdapter mDayItemAdapter;
    DayItemViewModel dayItemModel;

    TextView dayItemDate;
    String selectDate;
    public DayItemFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            selectDate=((MainActivity) Objects.requireNonNull(getActivity())).selectDate;
            dayItemDate.setText(selectDate);
//            Log.i("DayItemFregment","selectDateInsert 실행");
//            dayItemModel.selectDateInsert(selectDate);
//            //해당 프레그먼트가 열리는 순간 $$이때 내부에 있는 데이터들을 바꿔야한다.
        }
        else{

            //해당프레그먼트가 보이지 않게 됐을때
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static DayItemFragment newInstance(String todayDate) {
        if (dayItemFragment != null) {dayItemFragment = new DayItemFragment();}
//        Bundle args = new Bundle();
//        args.putString(TODAY_DATE,todayDate);
//        dayItemFragment.setArguments(args);
        return dayItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dayitem_list, container, false);

        //viewmodel 생성
        dayItemModel = ViewModelProviders.of(this).get(DayItemViewModel.class);
        dayItemDate = (TextView)view.findViewById(R.id.dayItem_List_Date);
        TextView fragment2Title = (TextView)view.findViewById(R.id.fragment2Title);
//        TextView fragment2Memo = (TextView)view.findViewById(R.id.fragment2memo);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.list);
        CheckableImageButton dayItemImg = (CheckableImageButton)view.findViewById(R.id.dayItemImg);
// Set the adapter

            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mDayItemAdapter = new MyDayItemRecyclerViewAdapter(context);
            recyclerView.setAdapter(mDayItemAdapter);


        //argument에 데이터가 있으면 데이터를 dayitemDate에 넣어준다.
//        if(getArguments()!= null){
//        String selectDate=getArguments().getString(TODAY_DATE);
//        dayItemDate.setText(selectDate);
//        }

        //day item 데이터 베이스의 데이터 교체시 콜백함수 (데이터 관찰)
        dayItemModel.getAll().observe(this, dayItemVOS -> {
            mDayItemAdapter.setData(dayItemVOS);
            mDayItemAdapter.notifyDataSetChanged();
        });
        Log.i("DayItemFregment","selectDateInsert 실행");
        dayItemModel.selectDateInsert(selectDate);
        //        클릭리스너
        mDayItemAdapter.setMyDayItemClickedListener(new MyDayItemRecyclerViewAdapter.DayItemClickedListener() {
            @Override
            public void dayItemClicked(DayItemVO dayItemVO) {
                showInputDayItemDialog(dayItemVO.getId(),dayItemVO.getItemTime(),dayItemVO.getItemTitle(),dayItemVO.getItemContent());

            }
        });
        //롱클릭 리스너
//        mDayItemAdapter.setMyDayItemLongClickedListener(new MyDayItemRecyclerViewAdapter.DayItemLongClickedListener() {
//            @Override
//            public void dayItemLongClicked(DayItemVO dayItemVO) {
//                final String deleteId = dayItemVO.getId();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("메모 삭제");
//                builder.setMessage("메모를 삭제하시겠습니까?");
//                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.setNegativeButton("취소" ,null);
//                builder.show();
//
//            }
//        });
        return view;
    }
    private void showInputDayItemDialog(int id,String time,String title,String content) {
        InputDayItemDialog inputDayItemDialog = InputDayItemDialog.newInstance(id, time, title, content);
        inputDayItemDialog.setOnSaveButtonClickListener(new InputDayItemDialog.onSaveButtonClickListener() {
            @Override
            public void onSaveButtonClick(int mDayItemId, String mDayItemTime, String DayItemTitle, String DayItemContent) {

            }

            @Override
            public void onCancelButtonClick() {

            }
        });
            }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
