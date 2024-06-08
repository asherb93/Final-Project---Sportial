package com.example.sportial;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Response;

import java.util.List;

import javax.security.auth.callback.Callback;

public class ProfileCreationActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteCity;
    private AutoCompleteTextView autoCompleteCountry;
    FirebaseFunctions func = new FirebaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile_creation);

        autoCompleteCity = findViewById(R.id.ac_city);

        // Create an array of cities
        String[] cities = {"Jerusalem", "Tel Aviv", "Haifa", "Rishon LeZion", "Petah Tikva", "Ashdod", "Netanya", "Beersheba", "Holon", "Bnei Brak", "Ramat Gan", "Ashkelon", "Rehovot", "Herzliya", "Modi'in-Maccabim-Re'ut", "Kfar Saba", "Hadera", "Nazareth", "Lod", "Ra'anana", "Bat Yam", "Ramla", "Givatayim", "Kiryat Ata", "Kiryat Motzkin", "Kiryat Bialik", "Kiryat Yam", "Dimona", "Or Yehuda", "Eilat", "Nahariya", "Yavne", "Ma'alot-Tarshiha", "Karmiel", "Umm al-Fahm", "Afula", "Sderot", "Bet Shemesh", "Nazareth Illit", "Tiberias", "Acre", "Safed", "Arad", "Kiryat Gat", "Kiryat Malakhi", "Carmiel", "Yokneam Illit", "Givat Shmuel", "Mevaseret Zion", "Kfar Yona", "Nesher", "Tamra", "Kiryat Ono", "Kafr Qasim", "Shefar'am", "Jaljulia", "Tira", "Taibe", "Baqa al-Gharbiyye", "Arraba", "Kfar Qara", "Reineh", "Iksal", "Sakhnin", "Maghar", "Kfar Manda", "Deir Hanna", "Jisr az-Zarqa", "Kfar Yasif", "Hurfeish", "Rameh", "Bi'ina", "Majd al-Krum", "Kaukab Abu al-Hija", "Kfar Kama", "Peki'in", "Gush Halav", "Metula", "Yesod HaMa'ala", "Mitzpe Ramon", "Yeruham", "Netivot", "Ofakim", "Kiryat Malakhi", "Lehavim", "Arad", "Omer", "Kiryat Gat", "Sderot", "Netivot", "Ashkelon", "Kiryat Yam", "Kiryat Motzkin", "Kiryat Bialik", "Kiryat Ata", "Umm al-Fahm", "Afula", "Nazareth Illit"};
        // Create an adapter for the spinner
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cities);

        // Set the adapter for the spinner
        autoCompleteCity.setAdapter(cityAdapter);

        // Set an item click listener for the spinner
        autoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
            }
        });



        autoCompleteCountry = findViewById(R.id.ac_country);

        // Create an array of cities
        String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "The Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "The Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "São Tomé and Príncipe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"};
        // Create an adapter for the spinner
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries);

        // Set the adapter for the spinner
        autoCompleteCountry.setAdapter(countryAdapter);

        // Set an item click listener for the spinner
        autoCompleteCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
            }
        });


        Button btnContinue = findViewById(R.id.create_profile_button);
        EditText firstNameEditText=findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        Spinner daySpinner = findViewById(R.id.db_day);
        Spinner monthSpinner = findViewById(R.id.db_month);
        Spinner yearSpinner = findViewById(R.id.db_year);
        Spinner genderSpinner =findViewById(R.id.db_gender) ;
        EditText countryEditText = findViewById(R.id.ac_country);
        EditText cityEditText = findViewById(R.id.ac_city);

        btnContinue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    String firstNameStr = firstNameEditText.getText().toString();
                    String lastNameStr = lastNameEditText.getText().toString();
                    int userBirthDay = Integer.parseInt(daySpinner.getSelectedItem().toString());
                    String userBirthMonth = monthSpinner.getSelectedItem().toString();
                    int userBirthYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                    String genderStr = genderSpinner.getSelectedItem().toString();
                    String countryStr = countryEditText.getText().toString();
                    String cityStr = cityEditText.getText().toString();
                    func.uploadDetails(firstNameStr, lastNameStr, userBirthDay, userBirthMonth, userBirthYear, genderStr, countryStr, cityStr);
                    // Create an Intent object with the target activity class
                    Intent intent = new Intent(ProfileCreationActivity.this, ImageUploadActivity.class);
                    // Start the SignupActivity
                    startActivity(intent);
                }
                catch (Throwable t) {
                    Toast.makeText(ProfileCreationActivity.this, "Please fill up all fields ", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }



}