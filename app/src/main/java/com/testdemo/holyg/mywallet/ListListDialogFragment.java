package com.testdemo.holyg.mywallet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ListListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link ListListDialogFragment.Listener}.</p>
 */
public class ListListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_WORK_MODE = "work_mode";
    private static final String ARG_SHEET_INPUT = "new_sheet";
    private Listener mListener;
    private Sheet argSheet;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    private int mode = 0;

    // TODO: Customize parameters
    public static ListListDialogFragment newInstance(Sheet currentSheet,int mode) {
        final ListListDialogFragment fragment = new ListListDialogFragment();
        final Bundle args = new Bundle();
        //args.putInt(ARG_ITEM_COUNT, itemCount);
        args.putSerializable(ARG_SHEET_INPUT,currentSheet);
        args.putInt(ARG_WORK_MODE,mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        argSheet = (Sheet)this.getArguments().getSerializable(ARG_SHEET_INPUT);
        mode = this.getArguments().getInt(ARG_WORK_MODE);
        recyclerView.setAdapter(new ListAdapter(argSheet));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onListClicked(boolean status,Sheet sheet,int mode);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCommet;
        private RadioGroup radioGroup;
        private RadioButton radioButtonExpend;
        private RadioButton radioButtonIncome;
        private ImageView imageViewTransport;
        private ImageView imageViewFood;
        private ImageView imageViewClothes;
        private ImageView imageViewHealth;
        private EditText editTextAmount;
        private LinearLayout linearLayoutDate;
        private LinearLayout linearLayoutTime;
        private TextView textViewHour;
        private TextView textViewMinute;
        private TextView textViewYear;
        private TextView textViewMonth;
        private TextView textViewDay;
        private Button buttonConfirm;
        private Button buttonCancel;




        private void setImageDark (int type){
            if(type != RecAdapter.UNDEFINED){
                switch (argSheet.getWay()){
                    case RecAdapter.TRANSPORT:
                        imageViewTransport.setImageResource(R.drawable.transport_light);
                        break;
                    case RecAdapter.CLOTHES:
                        imageViewClothes.setImageResource(R.drawable.clothes_light);
                        break;
                    case RecAdapter.HEALTH:
                        imageViewHealth.setImageResource(R.drawable.health_light);
                        break;
                    case RecAdapter.MEAL:
                        imageViewFood.setImageResource(R.drawable.restaurant_light);
                        break;
                }
            }
        }



        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_list_list_dialog_item, parent, false));


            textViewCommet = itemView.findViewById(R.id.textViewComment);

            radioGroup = itemView.findViewById(R.id.radioGroup);
            radioButtonExpend = itemView.findViewById(R.id.radioButtonExpend);
            radioButtonIncome = itemView.findViewById(R.id.radioButtonIncome);

            imageViewTransport = itemView.findViewById(R.id.imageViewTransport);
            imageViewClothes =itemView.findViewById(R.id.imageViewClothes);
            imageViewFood = itemView.findViewById(R.id.imageViewMeal);
            imageViewHealth = itemView.findViewById(R.id.imageViewHealth);

            editTextAmount = itemView.findViewById(R.id.editTextAmount);
            editTextAmount.addTextChangedListener(new DecimalInputTextWatcher(editTextAmount,15,2));

            linearLayoutDate = itemView.findViewById(R.id.lineDate);
            linearLayoutTime = itemView.findViewById(R.id.lineTime);

            textViewHour = itemView.findViewById(R.id.textViewHour);
            textViewMinute = itemView.findViewById(R.id.textViewMinute);
            textViewYear = itemView.findViewById(R.id.textViewYear);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
            textViewDay = itemView.findViewById(R.id.textViewDay);

            buttonCancel = itemView.findViewById(R.id.buttonCancel);
            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);




            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int id = group.getCheckedRadioButtonId();
                    switch (id){
                        case R.id.radioButtonExpend:
                            argSheet.setType(RecAdapter.EXPEND);
                            imageViewFood.setVisibility(View.VISIBLE);
                            imageViewClothes.setVisibility(View.VISIBLE);
                            imageViewHealth.setVisibility(View.VISIBLE);
                            imageViewTransport.setVisibility(View.VISIBLE);
                            break;
                        case R.id.radioButtonIncome:
                            argSheet.setType(RecAdapter.INCOME);
                            imageViewFood.setVisibility(View.INVISIBLE);
                            imageViewClothes.setVisibility(View.INVISIBLE);
                            imageViewHealth.setVisibility(View.INVISIBLE);
                            imageViewTransport.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            });

            View.OnClickListener imageOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setImageDark(argSheet.getWay());
                    switch (v.getId()){
                        case R.id.imageViewClothes:
                            imageViewClothes.setImageResource(R.drawable.clothes_dark);
                            argSheet.setWay(RecAdapter.CLOTHES);
                            break;
                        case R.id.imageViewTransport:
                            imageViewTransport.setImageResource(R.drawable.transport_dark);
                            argSheet.setWay(RecAdapter.TRANSPORT);
                            break;
                        case R.id.imageViewMeal:
                            imageViewFood.setImageResource(R.drawable.restaurant_dark);
                            argSheet.setWay(RecAdapter.MEAL);
                            break;
                        case R.id.imageViewHealth:
                            imageViewHealth.setImageResource(R.drawable.health_dark);
                            argSheet.setWay(RecAdapter.HEALTH);
                            break;

                    }
                }
            };

            linearLayoutTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (hourOfDay<10){
                                textViewHour.setText("0" + String.valueOf(hourOfDay));
                            }
                            else {
                                textViewHour.setText(String.valueOf(hourOfDay));
                            }
                            if (minute<10) {
                                textViewMinute.setText("0" + String.valueOf(minute));
                            }
                            else {
                                textViewMinute.setText(String.valueOf(minute));
                            }
                        }
                    },Integer.valueOf(argSheet.getHour()),Integer.valueOf(argSheet.getMinute()),true);
                    timePickerDialog.setTitle("Time Picker");
                    timePickerDialog.show();
                }
            });

            linearLayoutDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            textViewYear.setText(String.valueOf(year));
                            textViewMonth.setText(String.valueOf(month));
                            textViewDay.setText(String.valueOf(dayOfMonth));
                        }
                    },Integer.valueOf(argSheet.getYear()),Integer.valueOf(argSheet.getMonth()),Integer.valueOf(argSheet.getDay()));
                    datePickerDialog.setTitle("Date Picker");
                    datePickerDialog.show();
                }
            });

            imageViewFood.setOnClickListener(imageOnClickListener);
            imageViewTransport.setOnClickListener(imageOnClickListener);
            imageViewHealth.setOnClickListener(imageOnClickListener);
            imageViewClothes.setOnClickListener(imageOnClickListener);



//            textViewCommet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        mListener.onListClicked(getAdapterPosition());
//                    }
//                }
//            });



            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onListClicked(false,null,mode);
                    }
                    dismiss();
                }
            });

            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        if (argSheet.getType()!=RecAdapter.UNDEFINED) {
                            argSheet.setComment(textViewCommet.getText().toString());
                            argSheet.setAmount(Double.valueOf(editTextAmount.getText().toString()));
                            argSheet.setHour(Integer.valueOf(textViewHour.getText().toString()));
                            argSheet.setMinute(Integer.valueOf(textViewMinute.getText().toString()));
                            argSheet.setYear(Integer.valueOf(textViewYear.getText().toString()));
                            argSheet.setMonth(Integer.valueOf(textViewMonth.getText().toString()));
                            argSheet.setDay(Integer.valueOf(textViewDay.getText().toString()));
                            mListener.onListClicked(true, argSheet,mode);
                            dismiss();
                        }
                        else {
                            Toast toast = new Toast(getContext());
                            toast.makeText(getContext(),"Not Set Account Type!",Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            });
        }
    }






    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount = 1;
        private Sheet currentSheet;

        ListAdapter(Sheet sheet) {
            currentSheet = sheet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textViewCommet.setText(currentSheet.getComment());
            holder.textViewDay.setText(currentSheet.getDay());
            holder.textViewMonth.setText(currentSheet.getMonth());
            holder.textViewYear.setText(currentSheet.getYear());
            holder.editTextAmount.setText(currentSheet.getValue());
            holder.textViewHour.setText(currentSheet.getHour());
            holder.textViewMinute.setText(currentSheet.getMinute());
            if(currentSheet.getType() == RecAdapter.EXPEND){
                holder.radioButtonExpend.toggle();
                switch (currentSheet.getWay()){
                    case RecAdapter.TRANSPORT:
                        holder.imageViewTransport.setImageResource(R.drawable.transport_dark);
                        break;
                    case RecAdapter.CLOTHES:
                        holder.imageViewClothes.setImageResource(R.drawable.clothes_dark);
                        break;
                    case RecAdapter.HEALTH:
                        holder.imageViewHealth.setImageResource(R.drawable.health_dark);
                        break;
                    case RecAdapter.MEAL:
                        holder.imageViewFood.setImageResource(R.drawable.restaurant_dark);
                        break;
                }
            }
            else if(currentSheet.getType() == RecAdapter.INCOME){
                holder.radioButtonIncome.toggle();
            }
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }
    }

}
