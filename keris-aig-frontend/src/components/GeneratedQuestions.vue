<template>
  <v-card class="mx-auto" style="padding-bottom:1px">
    <v-responsive :aspect-ratio="20/3">
      <v-card-title style="padding: 16px 16px 1px 16px;" class="font-weight-black">
        <v-icon class="mr-2">mdi-playlist-plus</v-icon>생성 문항 리스트
        <v-spacer></v-spacer>
        <span class="title">STEP 3</span>
        <v-btn
          class="ma-2"
          large
          rounded
          :loading="loading"
          :disabled="loading"
          outlined
          color="indigo"
          @click="downloadMergedHmlFiles"
        >
          문항 다운로드
          <template v-slot:loader>
            <span style="font-size:xx-small">Loading..</span>
          </template>
        </v-btn>
        <!--
        <v-btn
          class="ma-2"
          large
          rounded
          :loading="loading"
          :disabled="loading"
          outlined
          color="indigo"
          @click="downloadIndividualHmlFiles"
        >
          개별 HML 파일로 다운로드
          <template v-slot:loader>
            <span style="font-size:xx-small">Loading..</span>
          </template>
        </v-btn>
        -->
      </v-card-title>

      <v-list dense style="overflow-y:auto" class="mt-1">
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
                            <v-icon class="mr-2">mdi-file-question-outline</v-icon>
                            <v-list-item-content>
                              <v-list-item-title class="subtitle-1 font-weight-bold">문항 내용</v-list-item-title>
                            </v-list-item-content>
                          </v-list-item>
                          <iframe
                            v-bind:id="'gq_bodyhtml' + index"
                            src="../../question_view.html"
                            height="100px"
                            width="100%"
                            frameborder="0"
                            @load="setGeneratedQuestion('gq_bodyhtml', index, generatedQhtml.html.body_html)"
                          ></iframe>
                        </v-card>
                      </v-col>
                      <v-col cols="6" xs="6" sm="6" md="6" lg="6" xl="6">
                        <v-card raised class="mx-auto mr-1">
                          <v-list-item>
                            <v-icon class="mr-2">mdi-adchoices</v-icon>
                            <v-list-item-content>
                              <v-list-item-title class="subtitle-1 font-weight-bold">선택지</v-list-item-title>
                            </v-list-item-content>
                          </v-list-item>
                          <iframe
                            v-bind:id="'gq_listhtml' + index"
                            src="../../question_view.html"
                            height="100px"
                            width="100%"
                            frameborder="0"
                            @load="setGeneratedQuestion('gq_listhtml', index, generatedQhtml.html.list_html)"
                          ></iframe>
                        </v-card>
                      </v-col>
                    </v-row>
                  </v-list-item-content>
                </template>
              </v-list-item>
              <v-divider v-if="index + 1 < generatedQhtmls.length" :key="'divider'+index"></v-divider>
            </template>
          </v-list-item-group>
        </template>
        <template v-else>
          <v-list-item>
            <template>
              <v-list-item-content>
                <v-list-item-title class="subtitle-1" v-text="generatingMsg"></v-list-item-title>
              </v-list-item-content>
            </template>
          </v-list-item>
        </template>
      </v-list>
    </v-responsive>
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
      generatedQhmls: [],
      generatingMsg: "생성 문항을 다운로드 해주세요.",
      h: window.innerHeight - 595
    };
  },
  created() {
    this.$EventBus.$on(
      "generatedQuestions",
      function(generationQs) {
        /* eslint-disable no-console */

        if (this.generatedQhtmls.length > 0) this.generatedQhtmls = []; // 모든 미리보기 항목을 초기화 한다.
        if (this.generatedQhmls.length > 0) this.generatedQhmls = []; // 모든 선택 항목을 초기화 한다.
        this.generatingMsg = "Generating...";

        setTimeout(
          function() {
            this.displayGeneratedQuestions(generationQs);
          }.bind(this),
          100
        );
      }.bind(this) // EventBus에서는 Event 안에 this는 자신이 된다. 따라서 바인딩할때 이와 같이 처리해줘야 한다.
    );
  },
  methods: {
    downloadIndividualHmlFiles() {
      var zip = new JsZip(); // **ReferenceError: JSZip is not defined**
      var uri, idx, content;

      //console.log("downloadHmlFiles");
      //console.log(this.generatedQhmls[0]);

      let downloadedCount = 0;
      if (this.generatedQhmls.length == 0) {
        this.$EventBus.$emit(
          "popAlertMessageToHome",
          "문항 생성을 먼저 진행해 주세요."
        );
        return;
      } else {
        for (var i = 0; i < this.generatedQhmls.length; i++) {
          uri = "data:binary;base64," + this.generatedQhmls[i];
          idx = uri.indexOf("base64,") + "base64,".length; // or = 28 if you're sure about the prefix
          content = uri.substring(idx);
          zip.file("question-" + i + ".hwp", content, { base64: true });
          downloadedCount++;
        }
      }

      zip.generateAsync({ type: "blob" }).then(function(blob) {
        saveAs(blob, "questions.zip");
      });

      this.$EventBus.$emit(
        "generaionSnackBarToHome",
        downloadedCount +
          "개의 hwp 파일이 포함된 zip파일이 다운로드 되었습니다."
      );
    },
    downloadMergedHmlFiles() {
      if (this.generatedQhmls.length == 0) {
        this.$EventBus.$emit(
          "popAlertMessageToHome",
          "문항 생성을 먼저 진행해 주세요."
        );
        return;
      } else {
        var zip = new JsZip(); // **ReferenceError: JSZip is not defined**
        var templateQuestionsHmlDoc,
          templateQuestionsHml,
          templateAnswersHmlDoc,
          templateAnswersHml,
          decodedTexthml,
          decodedHmlDoc,
          questionSectionNode,
          answerSectionNode,
          sectionPnodeSnapshots,
          sectionPnode;
        var parser = new DOMParser();
        var q_index = 0; //모든 hml for loop 밖에 둔다.
        var sectionMode; //q: 문제/선택지, a: 답/해설 i: 문항정보 이후
        let downloadedCount = 0;

        // read sample hml
        // this.$local.get("../hml/hml_sample.hml").then(response => {
        //   decodedHmlDoc = parser.parseFromString(response.data, "text/xml");
        // });

        // read template hml
        this.$local.get("../hml/template.hml").then(response => {
          // console.log(template_hml);

          console.log("111");
          templateQuestionsHmlDoc = parser.parseFromString(
            response.data,
            "text/xml"
          );
          console.log(templateQuestionsHmlDoc);
          templateAnswersHmlDoc = parser.parseFromString(
            response.data,
            "text/xml"
          );
          console.log("222");
          for (var i = 0; i < this.generatedQhmls.length; i++) {
            decodedTexthml = this.$base64.decode(this.generatedQhmls[i]); // DECODING
            console.log(decodedTexthml);

            if (window.DOMParser) {
              console.log("window.DOMParser");
              decodedHmlDoc = parser.parseFromString(
                decodedTexthml,
                "text/xml"
              );
            } else {
              console.log("Microsoft.XMLDOM");
              decodedHmlDoc = new ActiveXObject("Microsoft.XMLDOM");
              decodedHmlDoc.async = false;
              decodedHmlDoc.loadXML(decodedTexthml);
            }

            console.log(decodedHmlDoc);
            console.log("333");
            console.log(decodedHmlDoc.evaluate);
            if (decodedHmlDoc.evaluate) {
              sectionPnodeSnapshots = decodedHmlDoc.evaluate(
                "/HWPML/BODY/SECTION/P",
                decodedHmlDoc.cloneNode(true),
                null,
                XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,
                null
              );

              for (var j = 0; j < sectionPnodeSnapshots.snapshotLength; j++) {
                //모든 P노드에 대한 Iterator를 가져온다.
                sectionPnode = sectionPnodeSnapshots.snapshotItem(j);

                if (
                  sectionPnode.getElementsByTagName("CHAR").length &&
                  sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                    .nodeValue == "(문제)"
                ) {
                  sectionMode = "q";
                  questionSectionNode = templateQuestionsHmlDoc.createElement(
                    "SECTION"
                  );
                  questionSectionNode.setAttribute("ID", q_index);
                  sectionPnode.getElementsByTagName(
                    "CHAR"
                  )[0].childNodes[0].nodeValue =
                    "(" + (q_index + 1) + "번 문제)";
                  questionSectionNode.appendChild(sectionPnode);
                } else if (
                  sectionPnode.getElementsByTagName("CHAR").length &&
                  sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                    .nodeValue == "(답)"
                ) {
                  sectionMode = "a";
                  answerSectionNode = templateAnswersHmlDoc.createElement(
                    "SECTION"
                  );
                  answerSectionNode.setAttribute("ID", q_index);
                  sectionPnode.getElementsByTagName(
                    "CHAR"
                  )[0].childNodes[0].nodeValue = "(" + (q_index + 1) + "번 답)";
                  answerSectionNode.appendChild(sectionPnode);
                } else if (
                  sectionPnode.getElementsByTagName("CHAR").length &&
                  (sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                    .nodeValue == "(문항정보)" ||
                    sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                      .nodeValue == "(난이도)" ||
                    sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                      .nodeValue == "(행동영역)" ||
                    sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                      .nodeValue == "(내용영역)" ||
                    sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                      .nodeValue == "(단원코드)")
                ) {
                  sectionMode = "i";
                } else {
                  if (sectionMode == "q") {
                    questionSectionNode.appendChild(sectionPnode);
                  } else if (sectionMode == "a") {
                    answerSectionNode.appendChild(sectionPnode);
                  } else {
                    if (sectionPnode.getElementsByTagName("CHAR").length) {
                      console.log(
                        sectionPnode.getElementsByTagName("CHAR")[0].firstChild
                          .nodeValue + " is skiped..."
                      );
                    } else {
                      console.log("NOT EXIST CHAR NODE....");
                    }
                  }
                }
              }

              q_index++;

              //console.log(questionSectionNode);
              //console.log(answerSectionNode); // (답) 부분은 API 부분 수정이 필요함.

              templateQuestionsHmlDoc
                .getElementsByTagName("BODY")[0]
                .appendChild(questionSectionNode);

              templateAnswersHmlDoc
                .getElementsByTagName("BODY")[0]
                .appendChild(answerSectionNode);

              // console.log(templateQuestionsHmlDoc);
              // console.log(templateAnswersHmlDoc);
            } else {
              console.log("444");
              var nodes = decodedHmlDoc.selectNodes("/HWPML/BODY/SECTION/P");
              console.log("nodes");
              console.log(nodes);
            }
          }

          //code for IE
          if (window.ActiveXObject) {
            templateQuestionsHml = this.$base64.encode(
              templateQuestionsHmlDoc.xml
            );

            templateAnswersHml = this.$base64.encode(templateAnswersHmlDoc.xml);
          }
          // code for Chrome, Safari, Firefox, Opera, etc.
          else {
            templateQuestionsHml = this.$base64.encode(
              new XMLSerializer().serializeToString(templateQuestionsHmlDoc)
            );

            templateAnswersHml = this.$base64.encode(
              new XMLSerializer().serializeToString(templateAnswersHmlDoc)
            );
          }

          //console.log(templateQuestionsHml);
          //console.log(templateAnswersHml);

          downloadedCount++;

          zip.file(
            "시험지.hwp",
            ("data:binary;base64," + templateQuestionsHml).substring(
              ("data:binary;base64," + templateQuestionsHml).indexOf(
                "base64,"
              ) + "base64,".length
            ),
            { base64: true }
          );

          zip.file(
            "해설지.hwp",
            ("data:binary;base64," + templateAnswersHml).substring(
              ("data:binary;base64," + templateAnswersHml).indexOf("base64,") +
                "base64,".length
            ),
            { base64: true }
          );

          zip.generateAsync({ type: "blob" }).then(function(blob) {
            saveAs(blob, "문제집.zip");
          });

          this.$EventBus.$emit(
            "generaionSnackBarToHome",
            downloadedCount + "파일이 다운로드 되었습니다."
          );
        });
      }
    },
    displayGeneratedQuestions(generationQs) {
      //console.log(generationQs);

      if (generationQs.length == 0) {
        this.$EventBus.$emit(
          "popAlertMessageToHome",
          "보여줄 생성 문항이 없습니다.! 관리자에게 문의하세요."
        );
        return;
      } else {
        // 멀티 선택으로 변경..
        //this.objective = generationQs[0].objective;
        //console.log(generationQs);

        let generatedCount = 0;
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

              // 임시로 주석, 나중에 주석 해제
              this.generatedQhtmls.push({
                objective: generationQs[i].objective,
                //html: generationQs[i].generationHtml.html_list[h].d[0] as is
                html: generationQs[i].generationHtml.html_list[h] // 변경된 API는 d 노드가 없다.
              });

              this.generatedQhmls.push(
                generationQs[i].generationHml.hml_list[h].hml //base64
              );

              generatedCount++;
            }
          }
        }

        this.generatingMsg = "생성 문항을 다운로드 해주세요.";

        this.$EventBus.$emit(
          "generaionSnackBarToHome",
          generatedCount + "개의 문항이 성공적으로 생성되었습니다."
        );
      }
    },
    setGeneratedQuestion(ref, index, html) {
      //console.log(ref + "/" + index + ":" + html);

      //console.log(document.getElementById(ref + index));
      setTimeout(function() {
        if (document.getElementById(ref + index))
          document.getElementById(ref + index).contentWindow.setHtml(html);
        //this.$refs[ref + q.qsno].contentWindow.setHtml(q.bodyhtml);
      }, 100);
    }
  }
};
</script>