import React from "react";
import {
  Dimensions,
  Text,
  TouchableOpacity,
  View,
  StyleSheet,
} from "react-native";
import { COLOR } from "../../../utils/constants";

const PaymentButton = (onPress) => {
  return (
    <TouchableOpacity onPress={onPress}>
      <View style={styles.paymentButton}>
        <Text style={styles.paymentText}>결제하기</Text>
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
  paymentText: {
    color: COLOR.WHITE,
    fontSize: 15,
    fontWeight: "600",
    textAlign: "center",
  },
});

export default PaymentButton;
