<template>
  <v-card>
    <v-card-title>
      학습 목표
      <v-spacer></v-spacer>
      <v-select
        v-model="selgradeunit"
        :items="gradeunits"
        append-outer-icon="search"
        hide-details
        label="선택"
        @click:append-outer="searchGradeUnits"
        style="max-width:250px"
      ></v-select>
    </v-card-title>
    <v-list shaped dense height="278" style="overflow-y:auto">
      <v-list-item-group v-model="selectedObj" active-class="primary--text">
        <template v-if="learning_objectives.length">
          <template v-for="(obj, index) in learning_objectives">
            <v-list-item :key="obj.lbno" @click="searchQuestionPreviewList(obj.lbno)">
              <template v-slot:default="{ active, toggle }">
                <v-list-item-content style="max-width:50px">
                  <v-list-item-title v-text="obj.lbno"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-content>
                  <v-list-item-title v-text="obj.objective"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-action style="max-width:50px">
                  <!-- list에서 로우 클릭 시 체크박스가 체크되려면 v-model을 반드시 active로 해줘야 한다.-->
                  <v-checkbox v-model="active" color="primary" @click="toggle"></v-checkbox>
                </v-list-item-action>
              </template>
            </v-list-item>
            <v-divider v-if="index + 1 < obj.length" :key="index"></v-divider>
          </template>
        </template>
        <template v-else>
          <v-list-item>
            <template>
              <v-list-item-content>
                <v-list-item-title>No data found!</v-list-item-title>
              </v-list-item-content>
            </template>
          </v-list-item>
        </template>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>
<script>
export default {
  data() {
    return {
      e1: "Select",
      search: "",
      selectedObj: null,
      selgradeunit: [],
      learning_objectives: [],
      gradeunits: []
    };
  },
  created() {
    this.$axios.get("/api/v1/kerisaig/query/gradeunit").then(response => {
      /* eslint-disable no-console */
      // console.log(response.data[i].schoolYear);
      for (var i = 0; i < response.data.length; i++) {
        if (response.data[i].unitValue != null) {
          this.gradeunits.push({
            text:
              response.data[i].eduGubn +
              " " +
              response.data[i].schoolYear +
              "학년 " +
              response.data[i].semester +
              "학기 " +
              (response.data[i].unitValue == null
                ? ""
                : response.data[i].unitValue),
            value: response.data[i].guno
          });
        }
      }
    });
  },
  methods: {
    searchGradeUnits() {
      /* eslint-disable no-console */
      //console.log(this.selgradeunit);

      if (this.selgradeunit !== "") {
        this.$axios
          .get("/api/v1/kerisaig/query/learningobjective/" + this.selgradeunit)
          .then(response => {
            /* eslint-disable no-console */
            //console.log(response.data.content);

            this.learning_objectives = response.data.content;
          });
      }
    },
    searchQuestionPreviewList(lbno) {
      /* eslint-disable no-console */
      console.log(this.selectedObj);

      this.$EventBus.$emit("queryQuestionPreviewList", lbno);
    }
  },
  computed: {
    msg() {
      const selectedRow = this.selected[0];
      return selectedRow
        ? `${selectedRow.no} ${selectedRow.objectives}`
        : "no data selected";
    }
  }
};
</script>