package csc1304.gr13.retailmanagercsc.invoiceTab;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.SellsInfo;
import csc1304.gr13.retailmanagercsc.databinding.FragmentSearchInvoiceTabFrgBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;

import java.util.ArrayList;
import java.util.Calendar;
/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class SearchInvoiceTabFrg extends Fragment {


    public static final String TAG = "csc1304.gr13.retailmanagercsc";
    private FragmentSearchInvoiceTabFrgBinding binding;
    private Calendar calendar;
    private int day;
    private int month;
    private int year;
    private String fromDate = "0";
    private String toDate = "0";
    private boolean validDate = false;
    private View view;


    private Context context;
    private DatePickerDialog datePickerDialog;
    private ArrayList<CustomerModel> customers;
    private Customer customer;
    private SellsInfo sellInfoDb;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_invoice_tab_frg, container, false);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        customer = new Customer(getActivity());
        customers = customer.getAllCustomer();//get all customer details from customer database
        sellInfoDb = new SellsInfo(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fromDataInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), dateFromListener,year,month,day);
                datePickerDialog.show();
            }
        });

        binding.toDateInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), dateToListener,year,month,day);
                datePickerDialog.show();
            }
        });


        final ArrayList<String > suggestionCustomer = new ArrayList<>();
        if (customer.haveAnyData()){
            for (CustomerModel customer: customers){
                suggestionCustomer.add(customer.getCustomerName());
            }
        }else {
            suggestionCustomer.add("No Customer Found");
        }

        ArrayAdapter<String > suggestionAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,suggestionCustomer);
        binding.selectByCustomerInSpinner.setAdapter(suggestionAdapter);
        binding.searchInBtn.setOnClickListener(btnClickListener);
    }
    DatePickerDialog.OnDateSetListener dateFromListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            binding.fromDataInBtn.setText(dayOfMonth+"."+month+"."+year);
            fromDate = year+month+dayOfMonth+"";
        }
    };
    DatePickerDialog.OnDateSetListener dateToListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            int fromDTemp = Integer.parseInt(fromDate);//yearMonthDay
            int toDTemp = Integer.parseInt(String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth));//yearMonthDay


            if (fromDTemp <= toDTemp){
                toDate = year+month+dayOfMonth+"";
                binding.toDateInBtn.setText(dayOfMonth+"."+month+"."+year);

            }else {
                Toast.makeText(getActivity(),"From date can,t be lower then to date",Toast.LENGTH_SHORT).show();
                binding.toDateInBtn.setText("Select to");
                toDate = "0";
            }

            if (!toDate.equals("0") && !fromDate.equals("0")){
                validDate =true;
            }else validDate = false;

        }
    };

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            view = v;

            if (TextUtils.isEmpty(binding.selectByInvoiceInEt.getText())){
                showToast("invoice empty");
                manageData();
            }else if (validDate){
                manageData();
            }else {

            }
        }
    };

    public void manageData(){
        Log.e("InvoiceNumber",binding.selectByInvoiceInEt.getText().toString());
        sellInfoDb.getSoldProductInfo(binding.selectByInvoiceInEt.getText().toString());
    }

    public void showToast(String str){
        Toast.makeText(getActivity(), str,Toast.LENGTH_SHORT).show();
    }
    public void showSnakeBar(String str){
        Snackbar.make(view, str,Snackbar.LENGTH_SHORT).show();
    }
}
