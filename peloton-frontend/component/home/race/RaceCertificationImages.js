import React from "react";
import { StyleSheet } from "react-native";
import Swiper from "react-native-swiper";
import RaceCertificationImage from "./RaceCertificationImage";

const RaceCertificationImages = ({ certifications }) => {
  console.log(certifications);
  return (
    <Swiper
      style={styles.container}
      loop={false}
      showsPagination={true}
      autoplay
      autoplayTimeout={2.5}
    >
      {certifications && certifications.length > 0 ? (
        certifications.map((item, index) => (
          <RaceCertificationImage key={index} item={item} />
        ))
      ) : (
        <RaceCertificationImage key={1} item={null} />
      )}
    </Swiper>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 600,
  },
  image: {
    flex: 1,
    resizeMode: "cover",
  },
});

export default RaceCertificationImages;
