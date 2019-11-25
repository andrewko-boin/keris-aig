<template>
  <div v-html="compiledHtml"></div>
</template>
<script>
export default {
  data() {
    return {
      fileName: "question_view.html",
      input: ""
    };
  },
  created() {
    this.fileName = this.$route.params.fileName;
    this.loadFile();
  },
  computed: {
    compiledHtml: function() {
      return this.input;
    }
  },
  methods: {
    loadFile() {
      this.$local
        .get("../../question_view.htm")
        .then(result => {
          this.input = result.data;
        })
        .catch(error => {
          console.error("error getting file");
        });
    }
  }
};
</script>