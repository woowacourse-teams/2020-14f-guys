import React from "react";
import { Dimensions, StyleSheet, Text, TouchableOpacity, View, } from "react-native";
import { COLOR } from "../../../utils/constants";

const PaymentButton = ({ isPayment, paymentButton, charge }) => {
  return isPayment() === true ? (
    <TouchableOpacity onPress={paymentButton}>
      <View style={styles.paymentButton}>
        <Text style={styles.paymentText}>결제하기</Text>
      </View>
    </TouchableOpacity>
  ) : (
    <TouchableOpacity onPress={charge}>
      <View style={styles.chargeButton}>
        <Text style={styles.paymentText}>충전하기</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  paymentButton: {
    width: Dimensions.get("window").width,
    height: Dimensions.get("window").height * 0.07,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.BLUE3,
  },
  chargeButton: {
    width: Dimensions.get("window").width,
    height: Dimensions.get("window").height * 0.07,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.RED,
  },
  paymentText: {
    color: COLOR.WHITE,
    fontSize: 15,
    fontWeight: "600",
    textAlign: "center",
  },
});

export default PaymentButton;
