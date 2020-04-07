package com.example.formulriolaboratrio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "MainActivity";

    private TextView mDisplayTime;
    private TextView mDysplayDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    RadioGroup radioGroup;
    RadioButton radioButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup= findViewById(R.id.radioGroup);
        Spinner spinner = (Spinner) findViewById(R.id.labSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labs_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        mDysplayDate = (TextView) findViewById(R.id.tvData);

        mDysplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDysplayDate.setText(date);
            }
        };

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new timePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button buttonSend = (Button) findViewById(R.id.buttonEnviar);
        buttonSend.setOnClickListener(new onClickListener());


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText(hourOfDay + ":" + minute);
    }


    private class onClickListener implements View.OnClickListener {
        final EditText textName = (EditText) findViewById(R.id.inputNome);
        final EditText textMat = (EditText) findViewById(R.id.inputMat);
        final EditText textEmail = (EditText) findViewById(R.id.inputEmail);
        final TextView textDate = (TextView) findViewById(R.id.tvData);
        final TextView textTime = (TextView) findViewById(R.id.tvTime);
        final Spinner textLab = (Spinner) findViewById(R.id.labSpinner);
        final CheckBox textBox1 = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox textBox2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox textBox3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox textBox4 = (CheckBox) findViewById(R.id.checkBox4);
        final ToggleButton textToggle =  (ToggleButton) findViewById(R.id.appCompatToggleButton);
        final EditText textObs = (EditText) findViewById(R.id.etMultiplo);




        public void onClick(View view) {
            String name = textName.getText().toString();
            String mat = textMat.getText().toString();
            String email = textEmail.getText().toString();
            String date = textDate.getText().toString();
            String time = textTime.getText().toString();
            String lab = textLab.getSelectedItem().toString();
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioId);
            String tipoReserva = textToggle.getText().toString();
            String obs = textObs.getText().toString();

            String box1="";
            String box2="";
            String box3="";
            String box4="";



            if (textBox1.isChecked()) {
                    box1 = textBox1.getText().toString() + "\n";
                }
            if (textBox2.isChecked()) {
                    box2 = textBox2.getText().toString() + "\n";
                }
            if (textBox3.isChecked()) {
                    box3 = textBox3.getText().toString() + "\n";
                }
            if (textBox4.isChecked()) {
                    box4 = textBox4.getText().toString() + "\n";
                }




            Intent send = new Intent(Intent.ACTION_SEND);
            send.putExtra(Intent.EXTRA_EMAIL, new String[]{"contato@dominio.com"});
            send.putExtra(Intent.EXTRA_TEXT,
                    "=================================" + "\n" +
                            "Dados para reserva de laboratório" + "\n" +
                            "================================" + "\n" +
                            "Dados pessoais \n" +
                            "================================" + "\n" +
                            "Nome: " + name + "\n" + "\n" +
                            "Matricula: " + mat + "\n" + "\n" +
                            "E-mail: " + email + "\n" + "\n" +
                            "================================" + "\n" +
                            "Dados da reserva" + "\n" +
                            "================================" + "\n" +
                            "Data da reserva: " + date + "\n" + "\n" +
                            "Horário da reserva: " + time + "\n" + "\n" +
                            "Escolha um laboratório: " + lab + "\n" + "\n" +
                            "Vai precisar de datashow? " + radioButton.getText() + "\n" + "\n" +
                            "configurações desejadas" + "\n"
                            + box1
                            + box2
                            + box3
                            + box4 + "\n" +
                            "Reserva prioritária? " + tipoReserva + "\n" + "\n" +
                            "observações: " + "\n" + obs + "\n");
            send.setType("plain/text");
            startActivity(Intent.createChooser(send, "sending mail..."));
        }
    }
}




