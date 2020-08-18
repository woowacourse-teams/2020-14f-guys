import React from "react";
import { StyleSheet, View } from "react-native";
import { createStackNavigator } from "@react-navigation/stack";

import Certification from "./Certification";
import CertificationSubmit from "./CertificationSubmit";

const CertificationStack = createStackNavigator();

const CertificationNavigationRoot = () => {
  return (
    <View style={styles.container}>
      <CertificationStack.Navigator initialRouteName="CertificationHome">
        <CertificationStack.Screen
          name="CertificationHome"
          component={Certification}
          options={{
            title: "인증 목록",
          }}
        />
        <CertificationStack.Screen
          name="CertificationSubmit"
          component={CertificationSubmit}
          options={{
            title: "인증",
          }}
        />
      </CertificationStack.Navigator>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default CertificationNavigationRoot;
