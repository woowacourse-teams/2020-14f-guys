import React, { useEffect } from "react";
import { StyleSheet, View } from "react-native";
import Announcement from "./Announcement";
import { logNav } from "../../utils/Analytics";

const Setting = () => {
  useEffect(() => logNav("Setting", "SettingHome"), []);

  return (
    <View style={styles.container}>
      <Announcement />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Setting;
