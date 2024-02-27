FROM node:14 AS build
WORKDIR /app
COPY . .
RUN npm install && chmod +x node_modules/.bin/vue-cli-service && npm run build 
FROM nginx:latest
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
