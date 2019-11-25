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
          <Questions />
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LearningObjectives from "../components/LearningObjectives";
import QuestionPreviewList from "../components/QuestionPreviewList";
import QuestionPreview from "../components/QuestionPreview";
import Questions from "../components/Questions";
import ListTest from "../components/ListTest";

export default {
  name: "home",

  components: {
    LearningObjectives,
    QuestionPreviewList,
    QuestionPreview,
    Questions,
    ListTest
  },

  data: () => ({
    //
  })
};
</script>
