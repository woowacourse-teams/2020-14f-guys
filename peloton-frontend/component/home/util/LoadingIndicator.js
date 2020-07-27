import React from "react";
import { ActivityIndicator, StyleSheet, View } from "react-native";
import { useRecoilValue } from "recoil";

import { COLOR } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";

const LoadingIndicator = ({ children }) => {
  const isLoading = useRecoilValue(loadingState);

  return (
    <View style={styles.container}>
      {children}
      {isLoading && (
        <View style={styles.loadingIndicator}>
          <ActivityIndicator size="large" color={COLOR.GRAY5} />
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  loadingIndicator: {
    position: "absolute",
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    backgroundColor: COLOR.GRAY1,
    opacity: 0.8,
    justifyContent: "center",
    alignItems: "center",
  },
});

export default LoadingIndicator;
