<template>
  <v-row no-gutters>
    <v-form v-model="valid">
      <v-container>
        <v-row>
          <v-col cols="12" xs="6" sm="6" md="6" lg="6" xl="6">
            <v-select
              v-model="e2"
              :items="states"
              append-outer-icon="search"
              hide-details
              label="Select"
              single-line
            ></v-select>
          </v-col>
          <v-col cols="12" xs="6" sm="6" md="6" lg="6" xl="6">
            <v-text-field
              v-model="lastname"
              :rules="nameRules"
              :counter="10"
              label="Last name"
              required
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </v-row>
</template>
<script>
export default {
  name: "search",

  data() {
    return {
      e1: "Florida",
      e2: "Texas",
      e3: null,
      e4: null,
      items: [
        { text: "State 1" },
        { text: "State 2" },
        { text: "State 3" },
        { text: "State 4" },
        { text: "State 5" },
        { text: "State 6" },
        { text: "State 7" }
      ],
      states: [
        "Alabama",
        "Alaska",
        "American Samoa",
        "Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "District of Columbia",
        "Federated States of Micronesia",
        "Florida",
        "Georgia",
        "Guam",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Marshall Islands",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Montana",
        "Nebraska",
        "Nevada",
        "New Hampshire",
        "New Jersey",
        "New Mexico",
        "New York",
        "North Carolina",
        "North Dakota",
        "Northern Mariana Islands",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Palau",
        "Pennsylvania",
        "Puerto Rico",
        "Rhode Island",
        "South Carolina",
        "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Vermont",
        "Virgin Island",
        "Virginia",
        "Washington",
        "West Virginia",
        "Wisconsin",
        "Wyoming"
      ]
    };
  }
};
</script>