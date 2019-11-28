<template>
  <v-container fluid>
    <v-row></v-row>

    <v-row>
      <!-- Learning Objectives -->
      <v-col xs="12" sm="12" md="12" lg="5" xl="5">
        <v-card class="mr-1 pa-1" outlined tile>
          <LearningObjectives />
        </v-card>
      </v-col>
      <!-- Question Preview List-->
      <v-col xs="12" sm="12" md="12" lg="7" xl="7">
        <v-card class="mr-1 pa-1" outlined tile>
          <QuestionPreviewList />
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <!-- Generated Questions-->
      <v-col xs="12" sm="12" md="12" lg="12" xl="12">
        <v-card outlined tile>
          <GeneratedQuestions />
        </v-card>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-dialog v-model="alertDialog" persistent max-width="290" @keydown="alertDialog = false">
        <v-card>
          <v-card-title class="headline text-red">ALERT</v-card-title>
          <v-card-text v-text="alertMessage">Loading...</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="alertDialog = false">CLOSE</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

    <v-row align-content="center" justify="center">
      <v-dialog
        v-model="progressDialog"
        persistent
        max-width="350"
        @keydown="progressDialog = false"
      >
        <v-card>
          <v-col class="subtitle-1 text-center" cols="12" v-text="progressMessage"></v-col>
          <v-col cols="12">
            <v-progress-linear color="deep-purple accent-4" indeterminate rounded height="6"></v-progress-linear>
          </v-col>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
import LearningObjectives from "../components/LearningObjectives";
import QuestionPreviewList from "../components/QuestionPreviewList";
import GeneratedQuestions from "../components/GeneratedQuestions";

export default {
  name: "home",
  components: {
    LearningObjectives,
    QuestionPreviewList,
    GeneratedQuestions
  },
  data() {
    return {
      alertDialog: false,
      alertMessage: null,
      progressDialog: false,
      progressMessage: "Generating..."
    };
  },
  created() {
    this.$EventBus.$on(
      "popAlertMessageToHome",
      function(alertMessage) {
        this.popAlertMessage(alertMessage);
      }.bind(this)
    );

    this.$EventBus.$on(
      "popProgressBarToHome",
      function(progressMessage, isStart) {
        this.popProgressBar(progressMessage, isStart);
      }.bind(this)
    );
  },
  methods: {
    popAlertMessage(alertMessage) {
      this.alertDialog = true;
      this.alertMessage = alertMessage;
    },
    popProgressBar(progressMessage, isStart) {
      this.progressDialog = isStart;
      this.progressMessage = progressMessage;
    }
  }
};
</script>
