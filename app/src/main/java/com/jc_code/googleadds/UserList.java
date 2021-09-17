package com.jc_code.googleadds;

import static com.jc_code.googleadds.Util.*;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.jc_code.googleadds.adapters.UserAdapter;
import com.jc_code.googleadds.databinding.FragmentUserListBinding;
import com.jc_code.googleadds.modals.AccountModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserList extends Fragment {

    FragmentUserListBinding binding;
    String email;
    boolean isAccountUploading = false;
    UserAdapter userAdapter;
    RequestQueue queue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

        setupRecyclerview();
        getItems();


        binding.btnUploadAccount.setOnClickListener(vi -> {
            showDialogToEnterEmail();
        });
    }

    private void showDialogToEnterEmail() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.email_view, null);
        EditText etEmail = view.findViewById(R.id.email);
        new AlertDialog.Builder(requireActivity())
                .setTitle("Enter Email")
                .setMessage("email address is required to upload account data into user's account")
                .setView(view)
                .setPositiveButton("Upload", (dialog, id) -> {
                    email = etEmail.getText().toString();
                    if (email.isEmpty()) {
                        Toast.makeText(requireActivity(), "user's email could not be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog.dismiss();
                    uploadToDatabase();
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void uploadToDatabase() {

        Toast.makeText(requireActivity(), "Uploading !!", Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        isAccountUploading = true;
        for (int i = 0; i < userAdapter.getItemCount(); i++) {
            String doId = String.valueOf(System.currentTimeMillis());
            db.collection("Users").document(doId).set(getAccountUploadMap(userAdapter.getCurrentList().get(i), doId));
            binding.tvAccountFound.setText("Uploaded " + i + "/" + userAdapter.getItemCount());
        }
        isAccountUploading = false;


    }

    private Object getAccountUploadMap(AccountModel accountModel, String dId) {
        Map<String, Object> map = new HashMap<>();
        map.put(ACCOUNT_NUMBER, 0);
        map.put(EXTRA_COLUMN_1, accountModel.getAccountNumber());
        map.put(PROJECT, accountModel.getProjectName());
        map.put(NAME, accountModel.getName());
        map.put(EMAIL, email);
        map.put(USERNAME, email);
        map.put(ADDRESS, accountModel.getAddress());
        map.put(ADDRESS_1, DEFAULT);
        map.put(ADDRESS_2, DEFAULT);
        map.put(MOB_NUM_1, accountModel.getMobile1());
        map.put(DOC_ID, dId);
        map.put(MOB_NUM_2, accountModel.getMobile2());
        map.put(SAN_DT, accountModel.getSanctionDate());
        map.put(TIMESTAMP, System.currentTimeMillis());
        map.put(SAN_AMOUNT, accountModel.getSanctionAmount());
        map.put(BANK_NAME, DEFAULT);
        map.put(BRANCH_NAME, DEFAULT);
        map.put(IMAGE, DEFAULT);
        map.put(IS_ACTIVE, true);
        map.put(LAT, 0);
        map.put(LONG, 0);
        map.put(LAT2, 0);
        map.put(LONG2, 0);
        map.put(EXTRA_COLUMN_2, DEFAULT);

        return map;
    }

    private void setupRecyclerview() {
        userAdapter = new UserAdapter();
        binding.recUser.setItemAnimator(new DefaultItemAnimator());
        binding.recUser.setAdapter(userAdapter);
    }

    private void getItems() {
        queue = Volley.newRequestQueue(requireActivity());
        String url = "https://script.google.com/macros/s/AKfycbyL3AMZTqTzp4_UTpK0yfwCMxQwQstz3t0dUAy_hTHNvyhIErHoshQRkNPN8lKz2HrI/exec";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    binding.progressBar.setVisibility(View.GONE);
                    Log.d(TAG, response);
                    try {
                        List<AccountModel> users = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        binding.tvAccountFound.setText("Accounts Found : " + (jsonArray.length() + 1));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Gson gson = new Gson();
                            AccountModel accountModel = gson.fromJson(jsonArray.get(i).toString(), AccountModel.class);
                            users.add(accountModel);
                        }
                        userAdapter.submitList(users);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "error found  : cause > " + e.getLocalizedMessage());
                    }
                }, error -> {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(requireActivity(), "something went wrong !!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "That didn't work! > cause " + error.getLocalizedMessage());
        });

        queue.add(stringRequest);
    }
}