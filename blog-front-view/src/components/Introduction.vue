<template>
  <div class="introduction">
    <el-card id="user-card" class="box-card tools-item" :body-style="{ padding: '0px' }">
      <div
        class="backimg"
        :style="{background: 'url(' + introduction.backGroundImage + ') center center',backgroundSize: '100%'}"
      >
        <div class="photo-wrapper clearfix">
          <div class="photo-wrapper-tip text-center">
            <img :src="introduction.avatar" alt class="about-photo" />
          </div>
        </div>
      </div>
      <p class="desc">{{introduction.name}}</p>
      <div
        id="rollText"
        class="m-margin-top"
        style="font-size: 15px;"
        v-if="introduction.rollText.length!=0"
      ></div>

      <div class="center extra content">
        <el-tooltip
          class="item"
          effect="dark"
          content="Github"
          placement="top"
          v-if="introduction.github"
        >
          <a :href="introduction.github" target="_blank">
            <svg
              style="width: 1.5em!important;height: 1.5em!important;"
              t="1588657335874"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="6878"
              width="200"
              height="200"
            >
              <path
                d="M0 520.886c0-69.368 13.51-135.697 40.498-199.02 26.987-63.323 63.322-117.826 109.006-163.51 45.65-45.65 100.154-81.985 163.51-109.006A502.289 502.289 0 0 1 512 8.92c69.335 0 135.663 13.477 198.986 40.497 63.356 26.988 117.86 63.323 163.51 109.007 45.684 45.65 82.02 100.154 109.006 163.51A502.289 502.289 0 0 1 1024 520.852c0 111.318-32.504 211.472-97.511 300.494-64.975 88.989-148.48 150.825-250.484 185.476-5.351 0-9.348-0.99-11.99-2.973-2.676-1.982-4.196-3.997-4.526-6.012a59.458 59.458 0 0 1-0.495-8.984 7.663 7.663 0 0 1-0.991-3.006v-128.99c0-40.63-14.336-75.314-43.008-103.986 76.667-13.345 134.011-41.819 171.999-85.487 37.987-43.669 57.013-96.52 57.013-158.522 0-58.005-18.663-108.346-56.022-150.99 13.345-42.678 11-87.668-6.97-135.003-18.697-1.322-39.011 1.85-61.01 9.513-22 7.663-38.318 14.831-49.02 21.47-10.637 6.673-20.316 13.016-28.97 19.027-38.68-10.669-81.854-16.02-129.486-16.02-47.7 0-90.509 5.351-128.529 16.02-7.333-5.35-15.855-11.164-25.5-17.507-9.68-6.342-26.493-14.005-50.507-22.99-23.982-9.018-45.65-12.85-65.008-11.495-18.663 47.996-20.645 93.646-5.979 136.984-36.665 42.678-54.998 92.986-54.998 150.99 0 62.002 18.663 114.689 55.99 157.994 37.326 43.339 94.67 72.01 171.998 86.016a142.303 142.303 0 0 0-39.969 70.029c-56.683 13.972-96.355 3.963-119.015-30.06-42.017-61.308-79.674-83.307-113.003-65.965-4.69 4.657-3.997 9.48 1.982 14.501 6.012 4.988 14.996 11.66 27.02 19.985 11.99 8.357 20.976 17.507 26.987 27.515 0.661 1.322 2.51 6.177 5.517 14.502a831.917 831.917 0 0 0 8.985 23.981c2.973 7.663 8.654 16.186 17.011 25.5 8.324 9.349 18.003 17.178 29.003 23.52 11 6.309 26.161 11 45.485 14.006 19.324 2.972 41.323 3.138 65.998 0.495v100.484c0 0.991-0.165 2.643-0.495 5.021-0.33 2.312-0.991 3.964-1.982 4.955-0.991 1.024-2.345 2.015-4.03 3.039a12.52 12.52 0 0 1-6.474 1.486c-2.676 0-6.012-0.33-10.009-0.99-101.343-35.345-183.825-97.182-247.51-185.51C31.842 731.037 0 631.577 0 520.92z"
                p-id="6879"
              />
            </svg>
          </a>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="QQ" placement="top" v-if="introduction.qq">
          <a :href="introduction.qq" target="_blank">
            <svg
              style="width: 1.5em!important;height: 1.5em!important;"
              t="1614337566776"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="8385"
              width="200"
              height="200"
            >
              <path
                d="M801.28 668.8c-21.333333 18.773333-49.92-61.226667-53.973333-49.066667A186.026667 186.026667 0 0 1 704 702.293333c-1.493333 1.706667 33.706667 14.506667 43.733333 41.6s28.16 67.413333-93.44 80.213334c-71.466667 7.68-123.093333-37.973333-128-37.546667a94.72 94.72 0 0 1-15.573333 0 144.213333 144.213333 0 0 1-16.64 0c-2.346667 0-26.026667 37.546667-132.053333 37.546667-82.133333 0-103.466667-51.626667-87.04-80.213334s44.16-36.693333 40.32-41.173333a220.373333 220.373333 0 0 1-40.32-67.413333 162.773333 162.773333 0 0 1-4.906667-15.573334c-2.986667-11.306667-25.813333 67.2-50.346667 49.066667s-21.333333-64-6.4-108.586667 56.32-87.68 56.746667-97.066666c1.706667-35.413333-3.413333-41.173333 0-50.56 7.893333-21.333333 17.28-12.8 17.28-23.466667 0-135.68 100.906667-245.76 225.28-245.76s225.28 110.08 225.28 245.76c0 5.12 13.44 0 20.053333 23.466667a250.453333 250.453333 0 0 1 0 50.56c0 12.8 34.133333 28.373333 52.266667 97.066666s0 100.906667-8.96 108.586667zM512 0a512 512 0 1 0 512 512A512 512 0 0 0 512 0z"
                fill="#249BE5"
                p-id="8386"
              />
            </svg>
          </a>
        </el-tooltip>
        <el-tooltip
          class="item"
          effect="dark"
          content="bilibili"
          placement="top"
          v-if="introduction.bilibili"
        >
          <a :href="introduction.bilibili" target="_blank">
            <svg
              style="width: 1.5em!important;height: 1.5em!important;"
              t="1614337365422"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="5205"
              width="200"
              height="200"
            >
              <path
                d="M509.504 0.8a508.448 508.448 0 1 0 0 1016.96 508.448 508.448 0 0 0 0-1016.96z"
                fill="#FF509B"
                p-id="5206"
              />
              <path
                d="M169.376 334.016c0 34.304 0 68.32 0.544 102.528 0.384 17.824 0.96 35.616 2.368 53.44 1.536 21.248 3.872 42.88 5.76 64.544 0 0.96 0.64 1.792 0.64 2.368 7.36-0.384 14.528-0.96 21.632-0.96 30.4-0.832 60.128 3.296 88.64 14.496 13.888 5.6 27.456 12.032 39.392 21.056 9.92 7.52 12.224 16.8 6.88 30.464-2.848 8.256-8.256 15.392-15.456 20.352-16.864 13.312-34.24 24.96-54.56 31.68a263.04 263.04 0 0 1-43.712 10.688c-19.328 2.88-39.04 4.256-58.752 6.176-4.064 0.64-7.968 0-12.224 0-1.92 0-2.56-0.96-2.56-2.88-0.16-8.704-0.96-17.792-1.504-26.88-0.96-11.52-2.144-23.04-3.52-34.56l-5.696-48.128c-1.984-16.992-4.32-33.856-6.24-50.688-1.92-16.672-3.52-33.088-5.76-49.568-2.752-18.752-5.312-37.312-8.16-56.416a506.784 506.784 0 0 0-12.928-65.92c-0.384-0.96 0.576-2.88 1.152-3.52A2543.904 2543.904 0 0 0 162.56 329.6c6.176-2.72 6.816-3.68 6.816 4.48v-0.096z m409.952 0c0 34.304 0 68.32 0.576 102.528 0.384 17.824 0.96 35.616 2.368 53.44 1.536 21.248 3.872 42.88 5.76 64.544 0 0.96 0.64 1.792 0.64 2.368 7.36-0.384 14.528-0.96 21.632-0.96 30.4-0.832 60.128 3.296 88.64 14.496 13.888 5.6 27.392 12.032 39.392 21.056 10.048 7.52 12.288 16.8 6.72 30.336-2.88 8.224-8.256 15.36-15.424 20.288-16.864 13.376-34.272 24.96-54.56 31.744a263.04 263.04 0 0 1-43.712 10.624c-19.392 2.912-39.104 4.32-58.816 6.24-4 0.576-7.968 0-12.224 0-1.92 0-2.56-0.96-2.56-2.88-0.16-8.704-0.864-17.792-1.44-26.88-0.96-11.552-2.144-23.104-3.52-34.624l-5.76-48.064c-1.92-17.056-4.256-33.92-6.176-50.752-1.92-16.64-3.52-33.088-5.856-49.504-2.72-18.752-5.216-37.312-8.064-56.48a506.784 506.784 0 0 0-12.992-65.888c-0.192-1.024 0.832-2.912 1.344-3.296 19.072-7.36 38.176-14.88 57.248-22.72 6.24-2.72 6.784-3.68 6.784 4.48v-0.096z m-110.848 183.008c10.112 0 20.352 0.192 30.4 0.64 2.56 0 3.872 1.92 3.872 4.16 0.256 5.312 0.64 10.144 0.64 15.36 0 23.584-0.384 47.616 0 71.168 0.32 19.072 0.96 37.76 1.28 56.864 0 0.96 0 2.336 0.384 4.448-10.816-0.064-21.696 0.256-31.936-0.064-0.96 0-2.56-1.6-2.56-2.56-1.44-16.832-2.784-33.6-4.352-50.432l-4.288-46.816c-1.28-15.904-2.848-32-4.256-48.128 0-0.96 0-1.92-0.384-3.328 3.68-0.832 7.424-1.28 11.2-1.312z m410.048 0c10.112 0 20.352 0.192 30.4 0.64 2.56 0 3.872 1.92 3.872 4.16 0.256 5.312 0.64 10.144 0.64 15.36 0 23.584-0.384 47.616 0 71.168 0.384 19.072 0.96 37.76 1.28 56.864 0 0.96 0 2.336 0.384 4.448-10.816-0.064-21.696 0.256-31.936-0.064-0.96 0-2.56-1.6-2.56-2.56-1.44-16.832-2.784-33.6-4.384-50.432l-4.256-46.816c-1.28-15.904-2.88-32-4.256-48.128 0-0.96 0-1.92-0.384-3.328 3.68-0.832 7.424-1.28 11.2-1.312z m-501.056-1.408c1.152 7.744 2.56 15.456 3.424 23.584 1.792 13.248 2.88 26.56 4.256 39.68 1.344 11.648 2.56 23.04 3.968 34.624 1.28 12.192 2.88 24.544 4.448 37.28 0.32 3.488 0.896 7.36 1.28 11.2-11.264 1.376-22.272 2.72-33.92 4.288a18171.84 18171.84 0 0 1-29.376-149.888c8.064-0.96 15.904-2.368 23.584-3.328 5.6-0.8 11.456-0.8 16.864-1.312 3.104-0.384 5.024 0.96 5.44 3.84z m410.048 0c1.216 7.744 2.56 15.456 3.424 23.584 1.792 13.248 2.944 26.56 4.32 39.68l3.84 34.624 4.448 37.28c0.384 3.488 0.96 7.36 1.312 11.2-11.232 1.376-22.24 2.72-33.888 4.288a15618.016 15618.016 0 0 1-29.376-149.888c8.064-0.96 15.904-2.368 23.68-3.328 5.568-0.8 11.36-0.8 16.768-1.312 3.104-0.384 5.024 0.96 5.44 3.84z m-363.2-140.16l2.88 43.68c1.28 19.712 2.88 39.424 4.256 59.136 0.96 14.304 1.536 28.448 2.88 42.816 1.344 15.648 2.88 31.168 4.256 46.56 1.344 11.2 2.368 23.04 3.296 34.24 1.344 12.16 2.752 24.576 3.52 37.312l0.96 10.624c0.032 1.92-0.896 2.272-2.816 1.92-10.048-0.96-19.712-1.536-30.4-2.496-9.088-91.84-16.864-184.448-34.24-276.992l17.184-1.92 20.16-1.28c5.6-0.416 7.68 0.896 8.096 6.4z m410.016 0l2.88 43.68c1.248 19.712 2.848 39.424 4.224 59.136 0.96 14.304 1.6 28.448 2.88 42.816 1.312 15.648 2.88 31.168 4.32 46.56 1.28 11.2 2.272 23.04 3.296 34.24 1.28 12.16 2.688 24.576 3.456 37.312l0.96 10.624c0.096 1.92-0.832 2.272-2.816 1.92-10.112-0.96-19.712-1.536-30.4-2.496-9.088-91.84-16.864-184.448-34.24-276.992l17.184-1.92 20.16-1.28c5.6-0.416 7.68 0.896 8.096 6.4z m-364.16 66.752v0.128c2.56 0 3.456 0.576 3.456 3.456-0.384 7.744 0 15.872 0 23.584v29.376c-5.76 0.64-11.008 0.96-16.224 1.28-1.6-18.688-2.912-37.664-4.448-57.824h17.248z m410.08 0v0.128c2.56 0 3.424 0.576 3.424 3.456-0.384 7.744 0 15.872 0 23.584v29.376c-5.76 0.64-11.008 0.96-16.224 1.28-1.6-18.688-2.912-37.664-4.448-57.824h17.248z m-382.784 0.256c2.88 0 4.288 0.96 4.288 4.48v52.96l-20.992-0.96V442.56h16.704v-0.064z m410.048 0c2.88 0 4.288 0.96 4.288 4.48v52.96l-20.992-0.96V442.56h16.704v-0.064z m-569.408-3.232c2.72-0.384 1.92 1.92 2.336 3.424l1.92 19.136c0.96 10.112 2.336 19.744 3.296 29.856v3.84l-15.648 2.752c-3.52-18.752-6.816-37.312-10.24-56.48 6.176-0.64 12.096-2.016 18.336-2.56z m410.048 0c2.72-0.384 1.92 1.92 2.336 3.424l1.92 19.136c0.96 10.112 2.336 19.744 3.296 29.856v3.84l-15.648 2.752c-3.52-18.752-6.816-37.312-10.24-56.48 6.08-0.64 12.096-2.016 18.336-2.56z m-382.656-3.136c1.92 0 2.944 1.216 3.392 3.52 1.696 17.792 3.296 35.168 5.216 53.536-6.816 0.832-13.184 0.96-20.352 1.536l-7.328-58.208c6.624-0.384 12.896-0.384 19.072-0.384z m410.112 0c1.92 0 2.88 1.216 3.296 3.52 1.728 17.792 3.328 35.168 5.216 53.536-6.784 0.832-13.152 0.96-20.352 1.536l-7.296-58.208c6.56-0.384 12.896-0.384 19.072-0.384h0.064z"
                fill="#FFFFFF"
                p-id="5207"
              />
              <path
                d="M208.096 592.256c2.912 20.864 5.856 41.92 8.704 63.616 17.824-13.376 34.784-26.464 52.032-39.456-2.272-2.912-49.536-21.696-60.736-24.16z m410.112 0c2.88 20.864 5.76 41.92 8.64 63.616 17.824-13.376 34.784-26.464 52.032-39.456-2.304-2.912-49.472-21.696-60.672-24.16z"
                fill="#F66E98"
                p-id="5208"
              />
            </svg>
          </a>
        </el-tooltip>
        <el-tooltip
          class="item"
          effect="dark"
          content="网易云"
          placement="top"
          v-if="introduction.netease"
        >
          <a :href="introduction.netease" target="_blank">
            <svg
              style="width: 1.5em!important;height: 1.5em!important;"
              t="1614337432588"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="7489"
              width="200"
              height="200"
            >
              <path
                d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z"
                fill="#E82202"
                p-id="7490"
              />
              <path
                d="M840.256 469.184a265.408 265.408 0 0 0-44.16-88.448 317.376 317.376 0 0 0-85.248-76.416 236.8 236.8 0 0 0-111.296-32.704l-16.32-61.248a31.36 31.36 0 0 1 8.384-28.288 44.032 44.032 0 0 1 28.288-14.592 80.128 80.128 0 0 1 44.16 14.592c4.928 2.624 8.384 6.784 13.248 10.112a44.8 44.8 0 0 0 64.512-54.784 51.776 51.776 0 0 0-17.664-21.632 184.192 184.192 0 0 0-69.376-33.472 132.672 132.672 0 0 0-68.48 0.896 129.152 129.152 0 0 0-47.296 29.184 126.848 126.848 0 0 0-32 40.192 122.048 122.048 0 0 0-6.784 88.448l10.624 40.832a221.312 221.312 0 0 0-122.816 84.864 199.808 199.808 0 0 0-33.536 121.92 193.088 193.088 0 0 0 10.624 57.6 159.424 159.424 0 0 0 62.72 81.344 144.512 144.512 0 0 0 105.984 19.84 150.592 150.592 0 0 0 121.6-152.896l-4.416-25.6c-9.28-35.776-19.2-71.104-27.84-106.496a139.264 139.264 0 0 1 37.056 14.144 246.144 246.144 0 0 1 37.056 27.2 186.496 186.496 0 0 1 56.128 86.144 220.48 220.48 0 0 1 0 122.496 196.224 196.224 0 0 1-16.832 39.744 239.808 239.808 0 0 1-130.752 112.192 302.016 302.016 0 0 1-101.184 18.112 258.432 258.432 0 0 1-120.64-29.184A285.824 285.824 0 0 1 251.456 600.384a289.792 289.792 0 0 1-15.936-140.8A284.48 284.48 0 0 1 416.256 231.616a44.8 44.8 0 0 0 10.112-77.76A59.072 59.072 0 0 0 376.896 151.232 372.928 372.928 0 0 0 153.344 416.576a380.032 380.032 0 0 0 100.288 350.336 353.472 353.472 0 0 0 193.536 102.4 364.032 364.032 0 0 0 107.392 0 353.472 353.472 0 0 0 146.24-51.2 332.096 332.096 0 0 0 132.544-162.56 323.264 323.264 0 0 0 17.664-84.864 294.4 294.4 0 0 0-10.752-101.504zM519.488 561.536a56.064 56.064 0 0 1-44.16-8.384 76.8 76.8 0 0 1-29.184-47.616 132.352 132.352 0 0 1 0-51.2 103.808 103.808 0 0 1 26.048-49.472 140.8 140.8 0 0 1 62.72-36.224l30.464 115.2a161.792 161.792 0 0 1 4.032 20.416 61.824 61.824 0 0 1-49.92 57.28z"
                fill="#FFFFFF"
                p-id="7491"
              />
            </svg>
          </a>
        </el-tooltip>
        <el-tooltip
          class="item"
          effect="dark"
          content="邮箱"
          placement="top"
          v-if="introduction.email"
        >
          <a :href="introduction.email" target="_blank">
            <svg
              style="width: 1.5em!important;height: 1.5em!important;"
              t="1614337206892"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="3047"
              width="200"
              height="200"
            >
              <path d="M0 512A512 512 0 1 0 512 0 512 512 0 0 0 0 512z" fill="#50D3C1" p-id="3048" />
              <path
                d="M506.794667 547.783111l283.676444-236.344889a24.888889 24.888889 0 0 0-4.522667-0.910222H227.669333a24.490667 24.490667 0 0 0-4.494222 0.910222l283.648 236.344889z m304.554666-211.911111a23.779556 23.779556 0 0 0-1.536-7.566222l-201.102222 167.623111 201.386667 201.443555a24.632889 24.632889 0 0 0 1.223111-6.172444V335.872z m-607.544889-7.566222a24.576 24.576 0 0 0-1.564444 7.566222v355.299556a25.344 25.344 0 0 0 1.251556 6.200888l201.472-201.443555z"
                fill="#FFFFFF"
                p-id="3049"
              />
              <path
                d="M589.112889 512.256l-74.211556 61.809778a12.430222 12.430222 0 0 1-8.106666 2.844444 12.544 12.544 0 0 1-8.135111-2.844444l-74.126223-61.781334-203.036444 203.036445a23.921778 23.921778 0 0 0 6.144 1.251555h558.336a24.177778 24.177778 0 0 0 6.229333-1.251555z"
                fill="#FFFFFF"
                p-id="3050"
              />
            </svg>
          </a>
        </el-tooltip>
      </div>
      <div>
        <el-collapse accordion>
          <el-collapse-item
            :title="item.title"
            :name="index"
            v-for="(item,index) in introduction.favorites"
            :key="index"
          >
            <div>{{ item.content }}</div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "introduction",
  computed: {
    ...mapState(["introduction"])
  },
  data() {
    return {};
  },
  methods: {},
  created() {},
  watch: {
    "introduction.rollText"() {
      if (this.introduction.rollText.length != 0) {
        //等待 id="rollText"的div加载完毕
        this.$nextTick(() => {
          this.rollText();
        });
      }
    }
  },
  methods: {
    rollText() {
      let r = document.getElementById("rollText");
      let l = "";
      let o = this.introduction.rollText.map(function(r) {
        return r + "";
      });
      let a = 2;
      let g = 1;
      let s = 5;
      let d = 75;
      let b = [
        "rgb(110,64,170)",
        "rgb(150,61,179)",
        "rgb(191,60,175)",
        "rgb(228,65,157)",
        "rgb(254,75,131)",
        "rgb(255,94,99)",
        "rgb(255,120,71)",
        "rgb(251,150,51)",
        "rgb(226,183,47)",
        "rgb(198,214,60)",
        "rgb(175,240,91)",
        "rgb(127,246,88)",
        "rgb(82,246,103)",
        "rgb(48,239,130)",
        "rgb(29,223,163)",
        "rgb(26,199,194)",
        "rgb(35,171,216)",
        "rgb(54,140,225)",
        "rgb(76,110,219)",
        "rgb(96,84,200)"
      ];
      let c = {
        text: "",
        prefixP: -s,
        skillI: 0,
        skillP: 0,
        direction: "forward",
        delay: a,
        step: g
      };
      function t() {
        return b[Math.floor(Math.random() * b.length)];
      }
      function e() {
        return String.fromCharCode(94 * Math.random() + 33);
      }
      function n(r) {
        for (var n = document.createDocumentFragment(), i = 0; r > i; i++) {
          let l = document.createElement("span");
          (l.textContent = e()), (l.style.color = t()), n.appendChild(l);
        }
        return n;
      }
      function i() {
        let t = o[c.skillI];
        c.step
          ? c.step--
          : ((c.step = g),
            c.prefixP < l.length
              ? (c.prefixP >= 0 && (c.text += l[c.prefixP]), c.prefixP++)
              : "forward" === c.direction
              ? c.skillP < t.length
                ? ((c.text += t[c.skillP]), c.skillP++)
                : c.delay
                ? c.delay--
                : ((c.direction = "backward"), (c.delay = a))
              : c.skillP > 0
              ? ((c.text = c.text.slice(0, -1)), c.skillP--)
              : ((c.skillI = (c.skillI + 1) % o.length),
                (c.direction = "forward"))),
          (r.textContent = c.text),
          r.appendChild(
            n(
              c.prefixP < l.length
                ? Math.min(s, s + c.prefixP)
                : Math.min(s, t.length - c.skillP)
            )
          ),
          setTimeout(i, d);
      }
      i();
    }
  }
};
</script>

<style scoped>
.box-card:hover {
  transform: translate(0, -2px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}
.box-card .item:hover {
  cursor: pointer;
}
.box-card span {
  font-weight: bold;
}
.card-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}
#user-card {
  text-align: center;
}
.photo-wrapper {
  width: 100%;
}
.photo-wrapper img {
  margin-top: 50px;
  left: 133px;
  width: 90px;
  height: 90px;
  border: 4px solid hsla(0, 0%, 100%, 0.4);
  border-radius: 50%;
  transition: all 0.3s;
  cursor: pointer;
}
.photo-wrapper img:hover {
  transform: scale(1.3) rotate(1turn);
}
.desc {
  font-weight: 700;
  font-size: 1.28571429em;
  line-height: 1.28571429em;
}
.backimg {
  border-top-right-radius: 9px;
  border-top-left-radius: 9px;

  width: 100%;
}
.extra {
  max-width: 100%;
  min-height: 0 !important;
  -webkit-box-flex: 0;
  -ms-flex-positive: 0;
  flex-grow: 0;
  border-top: 1px solid rgba(0, 0, 0, 0.05) !important;
  position: static;
  background: 0 0;
  width: auto;
  margin: 0 0;
  padding: 0.75em 1em;
  top: 0;
  left: 0;
  color: rgba(0, 0, 0, 0.4);
  -webkit-box-shadow: none;
  box-shadow: none;
  -webkit-transition: color 0.1s ease;
  transition: color 0.1s ease;
}
.content {
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  border: none;
  border-top: 1px solid rgba(34, 36, 38, 0.1);
  background: 0 0;
  margin: 0;
  padding: 1em 1em;
  -webkit-box-shadow: none;
  box-shadow: none;
  font-size: 1em;
  border-radius: 0;
}
.ibutton {
  text-align: center;
}
.el-collapse-item {
  padding-left: 15px;
  padding-right: 15px;
}
.el-collapse-item .el-collapse-item__content {
  padding-bottom: 10px;
}
</style>