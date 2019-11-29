<template>
  <v-card class="mx-auto">
    <v-card-title style="padding: 16px 16px 1px 16px;">
      생성 문항 리스트
      <v-spacer></v-spacer>
      <v-btn
        class="ma-2"
        large
        rounded
        :loading="loading"
        :disabled="loading"
        color="primary"
        @click="downloadHmlFiles"
      >
        저장
        <template v-slot:loader>
          <span style="font-size:xx-small">Loading..</span>
        </template>
      </v-btn>
    </v-card-title>

    <v-list dense max-height="400" class="mt-1" style="overflow-y:auto">
      <template v-if="generatedQhtmls.length">
        <v-list-item-group>
          <template v-for="(generatedQhtml, index) in generatedQhtmls">
            <v-list-item :key="'l_' + index">
              <template>
                <v-list-item-content style="max-width:70px">
                  <v-list-item-title v-text="index+1"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-content style="max-width:200px">
                  <v-list-item-title v-text="generatedQhtml.objective.lesson"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-content style="padding-top: 0px;padding-bottom: 0px">
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
                          v-bind:id="'bodyhtml' + index"
                          src="../../question_view.html"
                          height="100px"
                          width="100%"
                          frameborder="0"
                          @load="setGeneratedQuestion('bodyhtml', index, generatedQhtml.html.body_html)"
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
                          v-bind:id="'listhtml' + index"
                          src="../../question_view.html"
                          height="100px"
                          width="100%"
                          frameborder="0"
                          @load="setGeneratedQuestion('listhtml', index, generatedQhtml.html.list_html)"
                        ></iframe>
                      </v-card>
                    </v-col>
                  </v-row>
                </v-list-item-content>
              </template>
            </v-list-item>
            <v-divider v-if="index + 1 < generatedQhtmls.length" :key="index"></v-divider>
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
import JsZip from "jszip";
import saveAs from "file-saver";

export default {
  data() {
    return {
      loading: false,
      objective: null,
      generatedQhtmls: [],
      generatedQhmls: []
    };
  },
  created() {
    this.$EventBus.$on(
      "generatedQuestions",
      function(generationQs) {
        /* eslint-disable no-console */
        //console.log(generationQs);

        this.generatedQhtmls.length = 0;
        this.generatedQhmls.length = 0;
        this.displayGeneratedQuestions(generationQs);
      }.bind(this) // EventBus에서는 Event 안에 this는 자신이 된다. 따라서 바인딩할때 이와 같이 처리해줘야 한다.
    );
  },
  methods: {
    downloadHmlFiles() {
      var zip = new JsZip(); // **ReferenceError: JSZip is not defined**
      var uri, idx, content;

      //console.log("downloadHmlFiles");
      //console.log(this.generatedQhmls[0]);

      if (this.generatedQhmls.length == 0) {
        this.$EventBus.$emit(
          "popAlertMessageToHome",
          "다운로드 받을 hml 파일이 존재하지 않습니다! 관리자에게 문의하세요."
        );
        return;
      } else {
        for (var i = 0; i < this.generatedQhmls.length; i++) {
          uri = "data:binary;base64," + this.generatedQhmls[i];
          idx = uri.indexOf("base64,") + "base64,".length; // or = 28 if you're sure about the prefix
          content = uri.substring(idx);
          zip.file("question-" + i + ".hwp", content, { base64: true });
        }
      }

      zip.generateAsync({ type: "blob" }).then(function(blob) {
        saveAs(blob, "questions.zip");
      });
    },
    displayGeneratedQuestions(generationQs) {
      //console.log(generationQs);

      if (generationQs == 0) {
        this.$EventBus.$emit(
          "popAlertMessageToHome",
          "보여줄 생성 문항이 없습니다.! 관리자에게 문의하세요."
        );
        return;
      } else {
        // 멀티 선택으로 변경..
        //this.objective = generationQs[0].objective;
        //console.log(generationQs);
        for (var i = 0; i < generationQs.length; i++) {
          if (generationQs[i].generationHtml.html_list.length == 0) {
            this.$EventBus.$emit(
              "popAlertMessageToHome",
              "HTML로 변환된 생성 문항이 없습니다! 관리자에게 문의하세요."
            );
            return;
          } else {
            //console.log(JSON.stringify(generationQs[i]));
            for (
              var h = 0;
              h < generationQs[i].generationHtml.html_list.length;
              h++
            ) {
              //console.log(generationQs[i].generationHtml.html_list[h].d[0]);
              this.generatedQhtmls.push({
                objective: generationQs[i].objective,
                html: generationQs[i].generationHtml.html_list[h].d[0]
              });

              this.generatedQhmls.push(
                generationQs[i].generationHml.hml_list[h].hml //base64
              );
            }
          }
        }
        //console.log(this.generatedQhtmls);
      }
    },
    setGeneratedQuestion(ref, index, html) {
      //console.log(html);

      //console.log(ref + "/" + index + ":" + html);
      //console.log(document.getElementById(ref + index));
      setTimeout(function() {
        document.getElementById(ref + index).contentWindow.setHtml(html);
        //this.$refs[ref + q.qsno].contentWindow.setHtml(q.bodyhtml);
      }, 100);
    }
  }
};
</script>