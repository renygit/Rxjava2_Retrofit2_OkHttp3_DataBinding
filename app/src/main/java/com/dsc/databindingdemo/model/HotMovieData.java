package com.dsc.databindingdemo.model;

import java.util.List;

/**
 * Created by reny on 2017/3/8.
 */

public class HotMovieData{

    private int count;
    private int start;
    private int total;
    private String title;
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":8.5,"stars":"45","min":0}
         * genres : ["剧情","动作","科幻"]
         * title : 金刚狼3：殊死一战
         * casts : [{"alt":"https://movie.douban.com/celebrity/1010508/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/22036.jpg","large":"https://img5.doubanio.com/img/celebrity/large/22036.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/22036.jpg"},"name":"休·杰克曼","id":"1010508"},{"alt":"https://movie.douban.com/celebrity/1010540/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/50451.jpg","large":"https://img3.doubanio.com/img/celebrity/large/50451.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/50451.jpg"},"name":"帕特里克·斯图尔特","id":"1010540"},{"alt":"https://movie.douban.com/celebrity/1363476/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/CCMOQr5bsGAcel_avatar_uploaded1476526279.97.jpg","large":"https://img1.doubanio.com/img/celebrity/large/CCMOQr5bsGAcel_avatar_uploaded1476526279.97.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/CCMOQr5bsGAcel_avatar_uploaded1476526279.97.jpg"},"name":"达芙妮·基恩","id":"1363476"}]
         * collect_count : 95102
         * original_title : Logan
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1036395/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/11282.jpg","large":"https://img3.doubanio.com/img/celebrity/large/11282.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/11282.jpg"},"name":"詹姆斯·曼高德","id":"1036395"}]
         * year : 2017
         * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2431980130.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2431980130.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2431980130.jpg"}
         * alt : https://movie.douban.com/subject/25765735/
         * id : 25765735
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<CastsBean> casts;
        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean {
            /**
             * max : 10
             * average : 8.5
             * stars : 45
             * min : 0
             */

            private int max;
            private double average;
            private String stars;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2431980130.jpg
             * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2431980130.jpg
             * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2431980130.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1010508/
             * avatars : {"small":"https://img5.doubanio.com/img/celebrity/small/22036.jpg","large":"https://img5.doubanio.com/img/celebrity/large/22036.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/22036.jpg"}
             * name : 休·杰克曼
             * id : 1010508
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img5.doubanio.com/img/celebrity/small/22036.jpg
                 * large : https://img5.doubanio.com/img/celebrity/large/22036.jpg
                 * medium : https://img5.doubanio.com/img/celebrity/medium/22036.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1036395/
             * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/11282.jpg","large":"https://img3.doubanio.com/img/celebrity/large/11282.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/11282.jpg"}
             * name : 詹姆斯·曼高德
             * id : 1036395
             */

            private String alt;
            private AvatarsBeanX avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBeanX getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBeanX avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBeanX {
                /**
                 * small : https://img3.doubanio.com/img/celebrity/small/11282.jpg
                 * large : https://img3.doubanio.com/img/celebrity/large/11282.jpg
                 * medium : https://img3.doubanio.com/img/celebrity/medium/11282.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
