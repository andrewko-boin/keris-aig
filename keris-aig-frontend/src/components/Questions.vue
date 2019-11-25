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
        @click="loader = 'loading'"
      >
        저장
        <template v-slot:loader>
          <span style="font-size:xx-small">Loading..</span>
        </template>
      </v-btn>
    </v-card-title>

    <v-list dense max-height="300" style="overflow-y:auto">
      <v-list-item-group>
        <template v-for="(item, index) in items">
          <v-list-item :key="item.title">
            <template>
              <v-list-item-content style="max-width:70px">
                <v-list-item-title v-text="index+1"></v-list-item-title>
              </v-list-item-content>
              <v-list-item-content style="max-width:200px">
                <v-list-item-title>(소수)X(자연수)</v-list-item-title>
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
                        v-bind:ref="'bodyhtml'"
                        src="../../question_view.html"
                        height="100px"
                        width="100%"
                        frameborder="0"
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
                        v-bind:ref="'listhtml'"
                        src="../../question_view.html"
                        height="100px"
                        width="100%"
                        frameborder="0"
                      ></iframe>
                    </v-card>
                  </v-col>
                </v-row>
              </v-list-item-content>
            </template>
          </v-list-item>

          <v-divider v-if="index + 1 < items.length" :key="index"></v-divider>
        </template>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>
<script>
export default {
  data() {
    return {
      e1: "개수",
      selected: [2],
      items: [
        { header: "Today" },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
          title: "Brunch this weekend?",
          subtitle:
            "<span class='text--primary'>Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?<span class='text--primary'>Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
          title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
          subtitle:
            "<span class='text--primary'>to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend."
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
          title: "Oui oui",
          subtitle:
            "<span class='text--primary'>Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
          title: "Birthday gift",
          subtitle:
            "<span class='text--primary'>Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?"
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
          title: "Recipe to try",
          subtitle:
            "<span class='text--primary'>Britta Holt</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos."
        }
      ],
      qcount: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
      loader: null,
      loading: false
    };
  },
  watch: {
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    }
  }
};
</script>
<style>
.custom-loader {
  animation: loader 1s infinite;
  display: flex;
}
@-moz-keyframes loader {
  from {
    transform: rotate(0);
  }
  to {
    transform: rotate(360deg);
  }
}
@-webkit-keyframes loader {
  from {
    transform: rotate(0);
  }
  to {
    transform: rotate(360deg);
  }
}
@-o-keyframes loader {
  from {
    transform: rotate(0);
  }
  to {
    transform: rotate(360deg);
  }
}
@keyframes loader {
  from {
    transform: rotate(0);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>