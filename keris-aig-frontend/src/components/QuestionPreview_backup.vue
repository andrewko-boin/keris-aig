<template>
  <v-card class="mx-auto">
    <v-card-title>
      문항 미리보기
      <v-spacer></v-spacer>
      <v-select
        v-model="e1"
        :items="qcount"
        hide-details
        label="개수"
        single-line
        style="max-width:100px"
      ></v-select>
      <v-btn
        class="ma-2"
        large
        rounded
        :loading="loading"
        :disabled="loading"
        color="primary"
        @click="detectAndGenQuestion"
      >
        생성
        <template v-slot:loader>
          <span style="font-size:xx-small">Loading..</span>
        </template>
      </v-btn>
    </v-card-title>
    <v-row>
      <v-col cols="4" xs="12" sm="12" md="6" lg="3" xl="3">
        <v-card raised class="mx-auto">
          <v-list-item>
            <v-list-item-avatar color="grey" size="30"></v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="title">문항 내용</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <iframe
            ref="bodyhtml"
            src="../../question_view.html"
            style="width: 100%; height: 130px"
            height="300px"
            width="100%"
            frameborder="0"
          ></iframe>
        </v-card>
      </v-col>
      <v-col cols="4" xs="12" sm="12" md="6" lg="3" xl="3">
        <v-card raised class="mx-auto">
          <v-list-item>
            <v-list-item-avatar color="grey" size="30"></v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="title">선택지</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <iframe
            ref="listhtml"
            src="../../question_view.html"
            style="width: 100%; height: 130px"
            height="300px"
            width="100%"
            frameborder="0"
          ></iframe>
        </v-card>
      </v-col>
      <v-col cols="4" xs="12" sm="12" md="6" lg="3" xl="3">
        <v-card raised class="mx-auto">
          <v-list-item>
            <v-list-item-avatar color="grey" size="30"></v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="title">정답</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <iframe
            ref="answerhtml"
            src="../../question_view.html"
            style="width: 100%; height: 130px"
            height="300px"
            width="100%"
            frameborder="0"
          ></iframe>
        </v-card>
      </v-col>
      <v-col cols="4" xs="12" sm="12" md="6" lg="3" xl="3">
        <v-card raised class="mx-auto">
          <v-list-item>
            <v-list-item-avatar color="grey" size="30"></v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="title">해설</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <iframe
            ref="bodyexthtml"
            src="../../question_view.html"
            style="width: 100%; height: 130px"
            height="300px"
            width="100%"
            frameborder="0"
          ></iframe>
        </v-card>
      </v-col>
    </v-row>
  </v-card>
</template>
<script>
export default {
  data() {
    return {
      e1: "개수",
      qcount: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
      loading: false,
      iframe: {
        src: window.location.href,
        style: null,
        wrapperStyle: null
      },
      prevq: [],
      detection: [],
      generation: []
    };
  },
  watch: {},
  methods: {
    detectAndGenQuestion() {
      // 로딩 시작
      // this.loading = true;
      // 로딩 종료
      // this.loading = false;

      this.detectQuestion();
    },
    genQuestion() {
      /* eslint-disable no-console */
      console.log(this.prevq);

      this.$generation
        .post("/cms.iscream.cceapi.com/cms_api/create_question/", {
          params: {}
        })
        .then(response => {
          /* eslint-disable no-console */
          console.log(response.data);

          //this.learning_objectives = response.data.content;
        });
    },
    detectQuestion() {
      /* eslint-disable no-console */
      //console.log(this.prevq.qsno);

      /* eslint-disable no-console */
      // console.log(btoa(encodeURIComponent(escape("MA1507A01010102"))));
      // console.log(btoa(encodeURIComponent(escape("단답형"))));
      // console.log(btoa(encodeURIComponent(escape("중"))));

      console.log(this.$base64.encode("MA1507A01010102"));
      console.log(this.$base64.encode("단답형"));
      console.log(this.$base64.encode("중"));

      var jsonBody = {};

      jsonBody.service_id = this.$base64.encode(this.prevq.qsno);
      jsonBody.question_id =
        this.prevq.qsno == null ? "" : this.$base64.encode(this.prevq.qsno);
      jsonBody.question_type_cd_value =
        this.prevq.questionType == null
          ? ""
          : this.$base64.encode(this.prevq.questionType);
      jsonBody.difficulty_cd_value =
        this.prevq.defficulty == null
          ? ""
          : this.$base64.encode(this.prevq.defficulty);
      jsonBody.chapter_code =
        this.prevq.chapterCode == null
          ? ""
          : this.$base64.encode(this.prevq.chapterCode);
      jsonBody.category1 =
        this.prevq.category1 == null
          ? ""
          : this.$base64.encode(this.prevq.category1);
      jsonBody.category2 =
        this.prevq.category2 == null
          ? ""
          : this.$base64.encode(this.prevq.category2);
      jsonBody.category3 =
        this.prevq.category3 == null
          ? ""
          : this.$base64.encode(this.prevq.category3);
      jsonBody.category4 =
        this.prevq.category4 == null
          ? ""
          : this.$base64.encode(this.prevq.category4);
      jsonBody.category5 =
        this.prevq.category5 == null
          ? ""
          : this.$base64.encode(this.prevq.category5);
      jsonBody.etc_category1 =
        this.prevq.etcCategory1 == null
          ? ""
          : this.$base64.encode(this.prevq.etcCategory1);
      jsonBody.etc_category2 =
        this.prevq.etcCategory2 == null
          ? ""
          : this.$base64.encode(this.prevq.etcCategory2);
      jsonBody.etc_category3 =
        this.prevq.etcCategory3 == null
          ? ""
          : this.$base64.encode(this.prevq.etcCategory3);
      jsonBody.etc_category4 =
        this.prevq.etcCategory4 == null
          ? ""
          : this.$base64.encode(this.prevq.etcCategory4);
      jsonBody.etc_category5 =
        this.prevq.etcCategory5 == null
          ? ""
          : this.$base64.encode(this.prevq.etcCategory5);
      jsonBody.body_title_html = "";
      jsonBody.body_html =
        this.prevq.bodyhtml == null
          ? ""
          : this.$base64.encode(this.prevq.bodyhtml);
      jsonBody.body_ex_html = "";
      jsonBody.list_html =
        this.prevq.listhtml == null
          ? ""
          : this.$base64.encode(this.prevq.listhtml);
      jsonBody.answer_html =
        this.prevq.answerhtml == null
          ? ""
          : this.$base64.encode(this.prevq.answerhtml);
      jsonBody.explanation_html =
        this.prevq.bodyext == null
          ? ""
          : this.$base64.encode(this.prevq.bodyext);
      jsonBody.hint_html = "";

      console.log(JSON.stringify(jsonBody));

      this.$detection
        .post("/cms.iscream.cceapi.com/cms_api/model_information/", {
          params: jsonBody
        })
        .then(response => {
          /* eslint-disable no-console */
          console.log(response.data);

          //this.learning_objectives = response.data.content;
        });
    },
    base64EncodeUnicode(str) {
      var utf8Bytes = encodeURIComponent(str).replace(
        /%([0-9A-F]{2})/g,
        function(match, p1) {
          return String.fromCharCode("0x" + p1);
        }
      );

      return utf8Bytes;
    }
  },
  created() {
    this.$EventBus.$on(
      "setQuestionPreview",
      function(q) {
        /* eslint-disable no-console */
        // console.log("setQuestionPreview:" + lbno);

        this.prevq = q;
        this.$refs.bodyhtml.contentWindow.setHtml(q.bodyhtml);
        this.$refs.listhtml.contentWindow.setHtml(q.listhtml);
        this.$refs.answerhtml.contentWindow.setHtml(q.answerhtml);
        this.$refs.bodyexthtml.contentWindow.setHtml(q.bodyexthtml);
      }.bind(this)
    );
  }
};
</script>