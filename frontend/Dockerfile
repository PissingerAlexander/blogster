# Stage 1: build
FROM node:latest as build

WORKDIR /app
COPY package*.json .
RUN npm i
RUN npm i -g @angular/cli
COPY . .
RUN npm run build

# Stage 2: create webserver
FROM nginx:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
