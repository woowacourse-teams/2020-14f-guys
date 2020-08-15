import React, { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "./constants";

const THREE_LINE_HEIGHT = 65;
const INITIAL_MAXIMUM_LINE_NUMBER = 3;
const INFINITE_LINE_NUMBER = 10;

const ReadMore = ({ children, fontStyle }) => {
  const [isFirst, setIsFirst] = useState(true);
  const [firstHeight, setFirstHeight] = useState(0);
  const [isPressedReadMore, setIsPressedReadMore] = useState(false);

  const calculatedNumberOfLines = () => {
    if (firstHeight > THREE_LINE_HEIGHT && isPressedReadMore) {
      return INFINITE_LINE_NUMBER;
    }
    return INITIAL_MAXIMUM_LINE_NUMBER;
  };

  const renderButton = () => {
    if (firstHeight < THREE_LINE_HEIGHT) {
      return null;
    }
    return (
      <Text
        style={styles.readMoreButton}
        onPress={() => setIsPressedReadMore(!isPressedReadMore)}
      >
        {isPressedReadMore ? "줄이기" : "더보기"}
      </Text>
    );
  };

  const getOriginalHeight = (event) => {
    const { height } = event.nativeEvent.layout;
    if (isFirst) {
      setFirstHeight(height);
      setIsFirst(false);
    }
  };

  return (
    <View style={styles.container}>
      <Text
        style={[styles.description, fontStyle]}
        numberOfLines={calculatedNumberOfLines()}
        onLayout={getOriginalHeight}
      >
        {children}
      </Text>
      {renderButton()}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  description: {
    color: COLOR.BLACK,
    fontSize: 18,
    fontWeight: "300",
    lineHeight: 23,
  },
  readMoreButton: {
    color: COLOR.BLUE1,
    marginTop: 15,
  },
});

export default ReadMore;
