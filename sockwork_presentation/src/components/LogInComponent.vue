<script>
export default {
  data() {
    //data-binding: comment lier data avec template,
    return {
      inputs: { username: "", password: "" },
    };
  },
  methods: {
    async submit() {
      //   event.preventDefault();
      // console.log(this.inputs);
      //REMPLACER BY AXIOS
      const options = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.inputs),
      };

      const response = await fetch("http://localhost:8080/accounts/log-in", options);
       this.message(response);
    },
    message (response) {
        if(response.status === 401) {
            alert("Connection refusée. Credentials erronées")
            console.error("Dev is a failure!")
        } else {
          alert("Connection avec succès");
          const data = response.text();
          console.log(data);
        }
    }
  },
};
</script>

<template>
  <h1>Se connecter à un compte</h1>
  <form @submit.prevent="submit" novalidate>
    <label for="username">Nom d'utilisateur</label>
    <input
      type="text"
      name="username"
      id="username"
      v-model="inputs.username"
    />
    <label for="password">Password</label>
    <input
      type="password"
      name="password"
      id="password"
      v-model="inputs.password"
    />
    <button type="submit">Se connecter</button>
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  width: 20%;
}
h1 {
  color: blue;
}
label::after {
  content: "*";
  color: red;
}
button {
  width: 50%;
  height: 2rem;
}
input {
  height: 1.5rem;
}

</style>