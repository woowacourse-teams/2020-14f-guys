import React from "react";
import { Image, StyleSheet, View } from "react-native";
import Swiper from "react-native-swiper";

const RaceCertificationImage = () => {
  return (
    <View style={styles.container}>
      <Swiper
        style={styles.imageContainer}
        loop={false}
        showsPagination={false}
      >
        <Image
          style={styles.image}
          source={{
            uri:
              "https://i.pinimg.com/736x/5f/f3/d7/5ff3d71b5834971c30af475c99f67c02.jpg",
          }}
        />
        <Image
          style={styles.image}
          source={{
            uri:
              "https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F994A57495BB704E012",
          }}
        />
        <Image
          style={styles.image}
          source={{
            uri:
              "https://cdn.pixabay.com/photo/2019/08/01/12/36/illustration-4377408_960_720.png",
          }}
        />
      </Swiper>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 600,
  },
  imageContainer: {
    height: 600,
    borderRadius: 10,
    overflow: "hidden",
  },
  image: {
    height: 600,
    resizeMode: "cover",
  },
});

export default RaceCertificationImage;
