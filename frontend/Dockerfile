FROM node:22.3.0-alpine3.20 AS build
WORKDIR /usr/local/app
COPY package*.json .
RUN npm ci
COPY . .
RUN npm run build --prod

FROM nginx:1.26.1-perl
COPY --from=build /usr/local/app/dist/frontend/browser /usr/share/nginx/html/
COPY default.nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
