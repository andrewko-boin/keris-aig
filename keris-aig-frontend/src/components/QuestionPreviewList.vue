<template>
  <v-card>
    <v-card-title>
      문항 미리보기 목록
      <v-spacer></v-spacer>
      <v-select
        v-model="selcount"
        :items="qcount"
        hide-details
        label="문항당 생성개수"
        style="max-width:150px"
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
    <v-list dense height="265" style="overflow-y:auto">
      <template v-if="prevQuestions.length">
        <v-list-item-group multiple v-model="selectQ" active-class="primary--text">
          <template v-for="(q, index) in prevQuestions">
            <v-list-item :key="q.qsno" :value="q" @click="findQuestionPriview(q)">
              <template v-slot:default="{ active, toggle }">
                <v-list-item-content style="max-width:50px">
                  <v-list-item-title v-text="index+1"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-content>
                  <v-row>
                    <v-col cols="6" xs="6" sm="6" md="6" lg="6" xl="6">
                      <v-card raised class="mx-auto ml-1">
                        <v-list-item>
                          <v-list-item-avatar color="grey" size="20"></v-list-item-avatar>
                          <v-list-item-content>
                            <v-list-item-title class="subtitle-1 font-weight-bold">문항 내용</v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>
                        <iframe
                          v-bind:id="'bodyhtml' + q.qsno"
                          src="../../question_view.html"
                          height="100px"
                          width="100%"
                          frameborder="0"
                          @load="setPrevQuestion('bodyhtml', q)"
                        ></iframe>
                      </v-card>
                    </v-col>
                    <v-col cols="6" xs="6" sm="6" md="6" lg="6" xl="6">
                      <v-card raised class="mx-auto mr-1">
                        <v-list-item>
                          <v-list-item-avatar color="grey" size="20"></v-list-item-avatar>
                          <v-list-item-content>
                            <v-list-item-title class="subtitle-1 font-weight-bold">선택지</v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>
                        <iframe
                          v-bind:id="'listhtml' + q.qsno"
                          src="../../question_view.html"
                          height="100px"
                          width="100%"
                          frameborder="0"
                          @load="setPrevQuestion('listhtml', q)"
                        ></iframe>
                      </v-card>
                    </v-col>
                  </v-row>
                </v-list-item-content>
                <v-list-item-action style="max-width:50px">
                  <!-- list에서 로우 클릭 시 체크박스가 체크되려면 v-model을 반드시 active로 해줘야 한다.-->
                  <v-checkbox v-model="active" color="primary" @click="toggle"></v-checkbox>
                </v-list-item-action>
              </template>
            </v-list-item>
            <v-divider v-if="index + 1 < q.length" :key="index"></v-divider>
          </template>
        </v-list-item-group>
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
    </v-list>
  </v-card>
</template>
<script>
export default {
  data() {
    return {
      selcount: 0,
      qcount: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      selectQ: [],
      loading: false,
      detection: [],
      generation: [],
      prevQuestions: []
    };
  },
  created() {
    this.$EventBus.$on(
      "queryQuestionPreviewList",
      function(lbno) {
        /* eslint-disable no-console */
        // console.log("queryQuestionPreviewList:" + lbno);

        this.$axios
          .get("/api/v1/kerisaig/query/question/" + lbno)
          .then(response => {
            /* eslint-disable no-console */
            //console.log(response.data.content);

            this.prevQuestions = response.data.content;

            /* eslint-disable no-console */
            // console.log(
            //   "question_preview_list: " + this.prevQuestions.length
            // );
          });
      }.bind(this) // EventBus에서는 Event 안에 this는 자신이 된다. 따라서 바인딩할때 이와 같이 처리해줘야 한다.
    );
  },
  methods: {
    findQuestionPriview(q) {
      /* eslint-disable no-console */
      // console.log(q.lbno);
      // console.log(q.bodyhtml);
      // console.log(q.listhtml);
      // console.log(q.answerhtml);
      // console.log(q.bodyexthtml);

      this.$EventBus.$emit("setQuestionPreview", q);
    },
    setPrevQuestion(ref, q) {
      /* eslint-disable no-console */
      //console.log(this.$refs[ref + "-" + q.qsno]);
      //his.$refs[ref + q.qsno].contentWindow.setHtml(q.bodyhtml);

      //iframe.append.style = "width:100%;height:100px;frameborder:0";
      // src="../../question_view.html"
      //                     height="100px"
      //                     width="100%"
      //                     frameborder="0"

      //console.log(this.$refs[ref + q.qsno]);
      setTimeout(function() {
        if (ref == "bodyhtml")
          document
            .getElementById(ref + q.qsno)
            .contentWindow.setHtml(q.bodyhtml);
        else if (ref == "listhtml")
          document
            .getElementById(ref + q.qsno)
            .contentWindow.setHtml(q.listhtml);
        //this.$refs[ref + q.qsno].contentWindow.setHtml(q.bodyhtml);
      }, 100);

      // if (iframe) {
      //   console.log(this.$refs[ref + q.qsno].contentWindow);
      // }

      //this.$refs[ref + q.qsno].contentWindow.postMessage(q.bodyhtml, "*");
    },
    detectAndGenQuestion() {
      /* eslint-disable no-console */
      //console.log(this.prevq.qsno);

      //console.log(this.selectQ);

      if (this.selcount == 0) {
        alert("count = 0");
        return;
      }

      // 여러개의 문항에 대해서 호출해야 하므로, 이 로직은 나중에 활성화 한다.
      //if (this.selectQ.length > 0) {
      //for (var i = 0; i < this.selectQ.length; i++) {
      // for test
      var formBody = this.createReqDetectQSampleFormBody();
      // real..
      //var formBody = this.createReqDetectQFormBody(this.selectQ[i]);

      this.loading = true; //로딩 시작

      // 문항 디텍트
      this.$detection({
        method: "post",
        url: "/cms.iscream.cceapi.com/cms_api/model_information/",
        data: formBody,
        headers: { "Content-Type": "multipart/form-data" }
      })
        .then(result => {
          this.detection = result.data;

          // 문항 생성
          this.genQuestion();
          this.loading = false; // 로딩 종료
          //생성 완료 팝업 필요
          alert(this.selcount + "개 문항 생성 완료.");
          console.log(result.data);
        })
        .catch(result => {
          //handle error
          this.loading = false;
          console.log(result.data);
        });
    },
    genQuestion() {
      /* eslint-disable no-console */
      console.log(this.selectQ);

      //if (this.selectQ.length > 0) {
      //for (var i = 0; i < this.selectQ.length; i++) {
      // for test
      var formBody = this.createReqGenerateQSampleFormBody();
      // real..
      //var formBody = this.createReqDetectQFormBody(this.selectQ[i]);

      this.$generation({
        method: "post",
        url: "/cms.iscream.cceapi.com/cms_api/create_question/",
        data: formBody,
        headers: { "Content-Type": "multipart/form-data" }
      })
        .then(result => {
          this.generation = result.data;
          console.log(result.data);
        })
        .catch(function(response) {
          //handle error
          console.log(response);
        });
    },
    createReqDetectQFormBody(selectQ) {
      let form = new FormData();

      form.append("service_id", "");
      form.append("etc_service_id", "");
      form.append("category1", selectQ.category1);
      form.append("category2", selectQ.category2);
      form.append("category3", selectQ.category3);
      form.append("category4", selectQ.category4);
      form.append("category5", selectQ.category5);
      form.append("chapter_code", selectQ.chapterCode);
      form.append("etc_category1", selectQ.etcCategory1);
      form.append("etc_category2", selectQ.etcCategory2);
      form.append("etc_category3", selectQ.etcCategory3);
      form.append("etc_category4", selectQ.etcCategory4);
      form.append("etc_category5", selectQ.etcCategory5);
      form.append("etc_chapter_code", "");
      form.append("question_id", selectQ.qsno);
      form.append("subject_cd", "");
      form.append("subject_cd_value", "");
      form.append("body_title_html", "");
      form.append("body_html", selectQ.bodyhtml);
      form.append("body_ex_html", selectQ.bodyext);
      form.append("list_html", selectQ.listhtml);
      form.append("answer_html", selectQ.answerhtml);
      form.append("explanation_html", selectQ.bodyext);
      form.append("hint_html", "");
      form.append("help1_html", "");
      form.append("help2_html", "");
      form.append("help3_html", "");
      form.append("help4_html", "");
      form.append("help5_html", "");
      form.append("question_group_id", "");
      form.append("sentence_html", "");
      form.append("sub_sentence_id", "");
      form.append("sub_sentence_html", "");
      form.append("small_group_id", "");
      form.append("small_group_html", "");
      form.append("analysis_id", "");
      form.append("analysis_name", "");
      form.append("studytree_id", "");
      form.append("studytree_name", "");
      form.append("question_type_cd", "");
      form.append("question_type_cd_value", selectQ.questionType);
      form.append("f_choice_shape_cd", "");
      form.append("f_choice_shape_cd_value", "");
      form.append("f_test_yn", "");
      form.append("f_usage_cd", "");
      form.append("f_usage_cd_value", "");
      form.append("difficulty_cd", "");
      form.append("difficulty_cd_value", "");
      form.append("f_weight_yn", "");
      form.append("f_book_cd", "");
      form.append("f_book_cd_value", "");
      form.append("f_etc10_cd", "");
      form.append("f_etc10_cd_value", "");
      form.append("point", "");
      form.append("cc_answer", "");
      form.append("cc_legend", "");
      form.append("concept_ids", "");
      form.append("inspection_mode_cd", "");
      form.append("inspection_mode_cd_value", "");
      form.append("f_book_desc", "");
      form.append("f_listen", "");
      form.append("f_keyword", "");
      form.append("f_etc_txt01", "");
      form.append("f_etc_txt02", "");
      form.append("f_etc_txt03", "");
      form.append("f_etc_txt04", "");
      form.append("f_etc_txt05", "");

      return form;
    },
    createReqDetectQSampleFormBody() {
      let form = new FormData();

      form.append("service_id", "");
      form.append("etc_service_id", "");
      form.append("category1", "01 소인수분해");
      form.append("category2", "01 소수와 합성수");
      form.append("category3", "01 소수와 합성수");
      form.append("category4", "02 소수와 합성수");
      form.append("category5", "");
      form.append("chapter_code", "MA1507A01010102");
      form.append("etc_category1", "중학교 1학년 / 1학기");
      form.append("etc_category2", "소인수분해");
      form.append("etc_category3", "소인수분해");
      form.append("etc_category4", "소수와 합성수");
      form.append("etc_category5", "");
      form.append("etc_chapter_code", "");
      form.append("question_id", "76032");
      form.append("subject_cd", "");
      form.append("subject_cd_value", "");
      form.append("body_title_html", "");
      form.append("body_title_html", "");
      form.append(
        "body_html",
        '<div class="cdml_question"><div class="question_box"><p style="margin: 0px 0px 0px 0px;text-align: left;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;">[#NO#] <span style="vertical-align: baseline;">다음&nbsp; 중&nbsp; 소수는&nbsp; 모두&nbsp; 몇&nbsp; 개인지&nbsp; 구하시오.</span></p><p style="margin: 0px 0px 0px 0px;text-align: left;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;">&nbsp;<span style="vertical-align: baseline;"></span></p><div style="margin: 0px 0px 0px 0px;text-align: center;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;"><div style="display: inline-block;width: 95%;"><table style="min-width: 100%;margin: 0px 0px 0px 0px;" ><colgroup><col width="100%"/></colgroup><tr><td colspan="1" rowspan="1" style="padding: 2px 2px 2px 2px;border-top: 1px solid #000000;border-right: 1px solid #000000;border-bottom: 1px solid #000000;border-left: 1px solid #000000;background-color: transparent;vertical-align: middle;height: 34px;" class=""><p style="margin: 0px 0px 0px 0px;text-align: center;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;"><span class="ccctex " style="">$$ {1} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {7} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {9} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {13} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {19} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {27} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {33} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {45} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {57} $$</span><span style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$ {71} $$</span></p></td></tr></table></div></div><p style="margin: 0px 0px 0px 0px;text-align: left;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;">&nbsp;<span style="vertical-align: baseline;"></span></p><p style="margin: 0px 0px 0px 0px;text-align: right;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;"><span style="padding: 0px 0px 0px 0px;border-top: 1px solid #000000;border-right: 1px solid #000000;border-bottom: 1px solid #000000;border-left: 1px solid #000000;background-color: transparent;vertical-align: middle;width: 75px;height: 37px;" class=""><input type="text" class="cc-input" style="width: 75px;" value="" data-index="①"/></span><span style="vertical-align: baseline;">개</span></p></div></div>'
      );
      form.append("body_ex_html", "");
      form.append("list_html", "");
      form.append(
        "answer_html",
        '<div class="cdml_question">\n<div class="question_box">\n<p  style="margin: 0px 0px 0px 0px;text-align: left;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;"><span class="ccctex " style="">$$  {4}  $$</span></p>\r\n\n</div>\n</div>\n'
      );
      form.append(
        "explanation_html",
        '<div class="cdml_question">\n<div class="question_box">\n<p  style="margin: 0px 0px 0px 0px;text-align: left;vertical-align: baseline;text-indent: 0px;line-height: 160.0%;border-top: 0;border-right: 0;border-bottom: 0;border-left: 0;"><span  style="vertical-align: baseline;">소수는&nbsp; </span><span class="ccctex " style="">$$  {7}  $$</span><span  style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$  {13}  $$</span><span  style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$  {19}  $$</span><span  style="vertical-align: baseline;">,&nbsp; </span><span class="ccctex " style="">$$  {71}  $$</span><span  style="vertical-align: baseline;">의&nbsp; </span><span class="ccctex " style="">$$  {4}  $$</span><span  style="vertical-align: baseline;">개이다.</span></p>\r\n\n</div>\n</div>\n'
      );
      form.append("hint_html", "");
      form.append("help1_html", "");
      form.append("help2_html", "");
      form.append("help3_html", "");
      form.append("help4_html", "");
      form.append("help5_html", "");
      form.append("question_group_id", "");
      form.append("sentence_html", "");
      form.append("sub_sentence_id", "");
      form.append("sub_sentence_html", "");
      form.append("small_group_id", "");
      form.append("small_group_html", "");
      form.append("analysis_id", "");
      form.append("analysis_name", "");
      form.append("studytree_id", "");
      form.append("studytree_name", "");
      form.append("question_type_cd", "");
      form.append("question_type_cd_value", "");
      form.append("f_choice_shape_cd", "");
      form.append("f_choice_shape_cd_value", "");
      form.append("f_test_yn", "");
      form.append("f_usage_cd", "");
      form.append("f_usage_cd_value", "");
      form.append("difficulty_cd", "");
      form.append("difficulty_cd_value", "");
      form.append("f_weight_yn", "");
      form.append("f_book_cd", "");
      form.append("f_book_cd_value", "");
      form.append("f_etc10_cd", "");
      form.append("f_etc10_cd_value", "");
      form.append("point", "");
      form.append("cc_answer", "");
      form.append("cc_legend", "");
      form.append("concept_ids", "");
      form.append("inspection_mode_cd", "");
      form.append("inspection_mode_cd_value", "");
      form.append("f_book_desc", "");
      form.append("f_listen", "");
      form.append("f_keyword", "");
      form.append("f_etc_txt01", "");
      form.append("f_etc_txt02", "");
      form.append("f_etc_txt03", "");
      form.append("f_etc_txt04", "");
      form.append("f_etc_txt05", "");

      return form;
    },
    createReqGenerateQSampleFormBody() {
      let form = new FormData();

      form.append("request_count", this.selcount);
      form.append("service_id", "");
      form.append("etc_service_id", "");
      form.append("category1", "");
      form.append("category2", "");
      form.append("category3", "");
      form.append("category4", "");
      form.append("category5", "");
      form.append("chapter_code", "MA1507A01010102");
      form.append("etc_category1", "중학교 1학년 / 1학기");
      form.append("etc_category2", "소인수분해");
      form.append("etc_category3", "소인수분해");
      form.append("etc_category4", "소수와 합성수");
      form.append("etc_category5", "");
      form.append("etc_chapter_code", "");
      form.append("question_id", "76032");
      form.append("subject_cd", "");
      form.append("subject_cd_value", "");
      form.append("body_title_html", "");
      form.append("body_html", "");
      form.append("body_ex_html", "");
      form.append("list_html", "");
      form.append("answer_html", "");
      form.append("explanation_html", "");
      form.append("hint_html", "");
      form.append("help1_html", "");
      form.append("help2_html", "");
      form.append("help3_html", "");
      form.append("help4_html", "");
      form.append("help5_html", "");
      form.append("question_group_id", "");
      form.append("sentence_html", "");
      form.append("sub_sentence_id", "");
      form.append("sub_sentence_html", "");
      form.append("small_group_id", "");
      form.append("small_group_html", "");
      form.append("analysis_id", "");
      form.append("analysis_name", "이해력");
      form.append("studytree_id", "");
      form.append("studytree_name", "수와 연산");
      form.append("question_type_cd", "");
      form.append("question_type_cd_value", "");
      form.append("f_choice_shape_cd", "");
      form.append("f_choice_shape_cd_value", "");
      form.append("f_test_yn", "");
      form.append("f_usage_cd", "");
      form.append("f_usage_cd_value", "");
      form.append("difficulty_cd", "");
      form.append("difficulty_cd_value", "중");
      form.append("f_weight_yn", "");
      form.append("f_book_cd", "");
      form.append("f_book_cd_value", "");
      form.append("f_etc10_cd", "");
      form.append("f_etc10_cd_value", "");
      form.append("point", "");
      form.append("cc_answer", "");
      form.append("cc_legend", "");
      form.append("concept_ids", "");
      form.append("inspection_mode_cd", "");
      form.append("inspection_mode_cd_value", "");
      form.append("f_book_desc", "");
      form.append("f_listen", "");
      form.append("f_keyword", "");
      form.append("f_etc_txt01", "");
      form.append("f_etc_txt02", "");
      form.append("f_etc_txt03", "");
      form.append("f_etc_txt04", "");
      form.append("f_etc_txt05", "");
      form.append("model", JSON.stringify(this.detection));

      return form;
    },
    createReqGenerateQFormBody(count, selectQ) {
      // 구현해야 함.
    }
  }
};
</script>