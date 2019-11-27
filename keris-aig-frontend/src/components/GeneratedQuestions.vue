<template>
  <v-card class="mx-auto">
    <v-card-title>
      생성 문항 리스트
      <v-spacer></v-spacer>
      <v-btn
        class="ma-2"
        large
        rounded
        :loading="loading"
        :disabled="loading"
        color="primary"
        @click="saveHml"
      >
        저장
        <template v-slot:loader>
          <span style="font-size:xx-small">Loading..</span>
        </template>
      </v-btn>
    </v-card-title>

    <v-list dense max-height="300" style="overflow-y:auto">
      <template v-if="generatedQhtmls.length">
        <v-list-item-group>
          <template v-for="(generatedQhtml, index) in generatedQhtmls">
            <v-list-item :key="'l_' + index">
              <template>
                <v-list-item-content style="max-width:70px">
                  <v-list-item-title v-text="index+1"></v-list-item-title>
                </v-list-item-content>
                <v-list-item-content style="max-width:200px">
                  <v-list-item-title v-text="objective.lesson"></v-list-item-title>
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
                          v-bind:id="'bodyhtml' + index"
                          src="../../question_view.html"
                          height="100px"
                          width="100%"
                          frameborder="0"
                          @load="setGeneratedQuestion('bodyhtml', index, generatedQhtml.body_html)"
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
                          @load="setGeneratedQuestion('listhtml', index, generatedQhtml.list_html)"
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
export default {
  data() {
    return {
      loading: false,
      objective: null,
      generatedQhtmls: []
    };
  },
  created() {
    this.$EventBus.$on(
      "generatedQuestions",
      function(generationQs) {
        /* eslint-disable no-console */
        //console.log(generationQs);

        this.generatedQhtmls.length = 0;
        this.displayGeneratedQuestions(generationQs);
      }.bind(this) // EventBus에서는 Event 안에 this는 자신이 된다. 따라서 바인딩할때 이와 같이 처리해줘야 한다.
    );
  },
  methods: {
    saveHml() {
      console.log("saveHml");
    },
    displayGeneratedQuestions(generationQs) {
      if (generationQs.length == 0) {
        alert("generationQs.length == 0");
        return;
      } else {
        this.objective = generationQs[0].objective;
        //console.log(generationQs);
        for (var i = 0; i < generationQs.length; i++) {
          if (generationQs[i].generationHtml.html_list.length == 0) {
            alert("generationQs[i].generationHtml.html_list.length == 0");
            return;
          } else {
            //console.log(JSON.stringify(generationQs[i]));
            for (
              var h = 0;
              h < generationQs[i].generationHtml.html_list.length;
              h++
            ) {
              //console.log(generationQs[i].generationHtml.html_list[h].d[0]);
              this.generatedQhtmls.push(
                generationQs[i].generationHtml.html_list[h].d[0]
              );
            }
          }
        }
      }
    },
    setGeneratedQuestion(ref, index, html) {
      //console.log(html);

      //console.log(ref + "/" + index + ":" + html);
      console.log(document.getElementById(ref + index));
      setTimeout(function() {
        document.getElementById(ref + index).contentWindow.setHtml(html);
        //this.$refs[ref + q.qsno].contentWindow.setHtml(q.bodyhtml);
      }, 100);
    }
  }
};
</script>