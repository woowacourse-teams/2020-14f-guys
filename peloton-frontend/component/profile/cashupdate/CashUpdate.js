import React from "react";
import { StyleSheet, Text, TextInput, TouchableOpacity, View, } from "react-native";

const CashUpdate = () => {
  const [value, onChangeText] = React.useState("5000");

  return (
    <View style={styles.container}>
      <View style={styles.chargeContainer}>
        <View style={{ alignItems: "left", width: "100%" }}>
          <Text style={styles.chargeText}>충전 금액을 입력해주세요</Text>
          <TextInput
            style={styles.chargeInput}
            onChangeText={(text) => onChangeText(text)}
            value={value}
          />
        </View>
      </View>
      <View style={styles.buttonContainer}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => alert("로그아웃")}
        >
          <Text style={styles.buttonText}>충전하기</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#eceff0",
  },
  chargeContainer: {
    flex: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  chargeText: {
    marginLeft: "14%",
    color: "#334856",
    fontWeight: "bold",
    fontSize: 20,
    lineHeight: 21,
    letterSpacing: -0.36,
    marginBottom: 20,
  },
  chargeInput: {
    backgroundColor: "white",
    fontSize: 16,
    fontWeight: "300",
    color: "#628ca0",
    paddingLeft: "10%",
    height: 60,
    width: "90%",
    shadowColor: "rgba(27, 28, 32, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 30,
    shadowOpacity: 1,
    borderRadius: 10,
    alignSelf: "center",
  },
  buttonContainer: {
    flex: 2,
    alignItems: "center",
  },
  button: {
    marginTop: 50,
    width: 150,
    height: 50,
    borderRadius: 100,
    backgroundColor: "white",
    color: "#1b1c20",
    alignItems: "center",
    justifyContent: "center",

    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 1,
  },
  buttonText: {
    color: "#334856",
    fontSize: 14,
  },
});

export default CashUpdate;
